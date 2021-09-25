package com.yjrlab.tabdoctor.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.view.main.MainActivity;
import com.yjrlab.tabdoctor.model.UserModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.service.UserService;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class LoginActivity extends TabDoctorActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final int REQUEST_GOOGLE_LOGIN = 9001;
    public static final String INTENT_WARNING = "warning";

    private GoogleApiClient mGoogleApiClient;

    private CallbackManager facebookManager;
    private OAuthLogin naverManager;

    private Button mButtonGoogleLogin;
    private Button mButtonFacebookLogin;
    private Button mButtonNaverLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setLayout();
        initGoogleLogin();
        initFacebookLogin();
        initNaverLogin();

        String loginEmail = PreferenceUtils.loadEmail(getContext());
        if (loginEmail != null) {
            startLogin(loginEmail, null, null);
        }
    }

    private void startLogin(final String email, final String name, final String refer) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "로그인시 이메일 권한은 필수 입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        NetworkManager.retrofit
                .create(UserService.class)
                .getInfo(email, FirebaseInstanceId.getInstance().getToken())
                .enqueue(new NetworkCallback<UserModel>(getContext()) {
                    @Override
                    protected void onSuccess(UserModel content) {
                        super.onSuccess(content);

                        PreferenceUtils.saveEmail(getContext(), content.getEmail());
                        PreferenceUtils.saveGender(getContext(), content.getShowTypeEnum());
                        PreferenceUtils.saveUserId(getContext(), content.getId());
                        Dlog.d("login" + content.getId());
                        PreferenceUtils.saveNporg(getContext(), content.getNporgStatusEnum());

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra(INTENT_WARNING, true);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    protected void onFailure(String msg) {
                        //super.onFailure(msg);
                        if (refer != null) {
                            Intent signUpIntent = new Intent(getContext(), SignUpActivity.class);
                            signUpIntent.putExtra(SignUpActivity.INTENT_EMAIL, email);
                            signUpIntent.putExtra(SignUpActivity.INTENT_NAME, name);
                            signUpIntent.putExtra(SignUpActivity.INTENT_LOGIN_REFER, refer);
                            startActivity(signUpIntent);
                            finish();
                        }
                    }

                    @Override
                    protected void onFinish() {
                        super.onFinish();
                        if (refer == null) {
                            return;
                        }

                        if (refer.equals(UserModel.REFER_FACEBOOK)) {
                            LoginManager.getInstance().logOut();
                        } else if (refer.equals(UserModel.REFER_GOOGLE)) {
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient);

                        } else if (refer.equals(UserModel.REFER_NAVER)) {
                            naverManager.logout(getContext());
                        }
                    }
                });
//        NetworkManager.retrofit
//                .create(UserService.class)
//                .getInfo(email, FirebaseInstanceId.getInstance().getToken())
//                .enqueue(new NetworkCallback<UserModel>(getContext()) {
//                    @Override
//                    protected void onSuccess(UserModel content) {
//                        super.onSuccess(content);
//
//                        PreferenceUtils.saveEmail(getContext(), content.getEmail());
//                        PreferenceUtils.saveGender(getContext(), content.getShowTypeEnum());
//                        PreferenceUtils.saveUserId(getContext(), content.getId());
//                        Dlog.d("login"+content.getId());
//                        PreferenceUtils.saveNporg(getContext(),content.getNporgStatusEnum());
//
//                        Intent intent = new Intent(getContext(),MainActivity.class);
//                        intent.putExtra(INTENT_WARNING,true);
//                        startActivity(intent);
//                        finish();
//                    }
//
//                    @Override
//                    protected void onFailure(String msg) {
//                        //super.onFailure(msg);
//                        if (refer != null) {
//                            Intent signUpIntent = new Intent(getContext(), SignUpActivity.class);
//                            signUpIntent.putExtra(SignUpActivity.INTENT_EMAIL, email);
//                            signUpIntent.putExtra(SignUpActivity.INTENT_NAME, name);
//                            signUpIntent.putExtra(SignUpActivity.INTENT_LOGIN_REFER, refer);
//                            startActivity(signUpIntent);
//                            finish();
//                        }
//                    }
//                });

    }

    private void initFacebookLogin() {
        facebookManager = CallbackManager.Factory.create();
        LoginButton facebookLogin = (LoginButton) findViewById(R.id.facebookLogin);
        facebookLogin.setReadPermissions("public_profile", "email");
        facebookLogin.registerCallback(facebookManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String email = null;
                                    if (!object.isNull("email")) {
                                        email = object.getString("email");
                                    }
                                    String name = object.getString("name");
                                    startLogin(email, name, UserModel.REFER_FACEBOOK);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), "페이스북 로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "페이스북 로그인 실패(" + error.getLocalizedMessage() + ")", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initNaverLogin() {
        naverManager = OAuthLogin.getInstance();
        naverManager.init(this, "tY2XtDN3oW9Y801hs7xA", "hUqud805ly", "탭닥터");
    }

    private void setLayout() {
        mButtonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        mButtonGoogleLogin.setOnClickListener(this);
        mButtonFacebookLogin = (Button) findViewById(R.id.buttonFacebookLogin);
        mButtonFacebookLogin.setOnClickListener(this);
        mButtonNaverLogin = (Button) findViewById(R.id.buttonNaverLogin);
        mButtonNaverLogin.setOnClickListener(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Toast.makeText(getContext(), "구글 로그인 실패", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_GOOGLE_LOGIN) {
            handleGoogleLogin(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        } else {
            facebookManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleGoogleLogin(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String email = acct.getEmail();
            String name = acct.getDisplayName();
            startLogin(email, name, UserModel.REFER_GOOGLE);
        } else {
            Toast.makeText(getContext(), "구글 로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGoogleLogin:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, REQUEST_GOOGLE_LOGIN);
                break;
            case R.id.buttonFacebookLogin:
                findViewById(R.id.facebookLogin).callOnClick();
                break;
            case R.id.buttonNaverLogin:
                naverManager.startOauthLoginActivity(this, new OAuthLoginHandler() {
                    @Override
                    public void run(boolean success) {
                        if (success) {
                            String accessToken = naverManager.getAccessToken(getContext());
                            new RequestApiTask(getContext(), naverManager, accessToken)
                                    .setOnCompleteListener(new RequestApiTask.OnCompleteListener() {
                                        @Override
                                        public void onCompleted(String email, String name) {
                                            startLogin(email, name, UserModel.REFER_NAVER);
                                        }
                                    }).execute();
                        } else {
                            String errorCode = naverManager.getLastErrorCode(getContext()).getCode();
                            String errorDesc = naverManager.getLastErrorDesc(getContext());
//                            Toast.makeText(LoginActivity.this, "네이버 로그인 실패(Code:" + errorCode
//                                    + ", Desc:" + errorDesc + ")", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    private static class RequestApiTask extends AsyncTask<Void, Void, Void> {
        private String email;
        private String name;
        private OnCompleteListener listener;
        private OAuthLogin naverLogin;
        private Context context;
        private String token;

        interface OnCompleteListener {
            void onCompleted(String email, String name);
        }

        private RequestApiTask(Context context, OAuthLogin naverLogin, String token) {
            this.naverLogin = naverLogin;
            this.context = context;
            this.token = token;
        }

        private RequestApiTask setOnCompleteListener(OnCompleteListener listener) {
            this.listener = listener;
            return this;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml";
            String at = token;
            Pasingversiondata(naverLogin.requestApi(context, at, url));
            return null;

        }

        @Override
        protected void onPostExecute(Void content) {
            if (listener != null) {
                listener.onCompleted(email, name);
            }
        }


        private void Pasingversiondata(String data) { // xml 파싱
            // xmlPullParser
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new StringReader(data));
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            String startTag = parser.getName();
                            if (startTag.equals("email")) {
                                email = parser.nextText();
                            }
                            if (startTag.equals("name")) {
                                name = parser.nextText();
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                    }
                    eventType = parser.next();
                }


            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
        }

    }


}
