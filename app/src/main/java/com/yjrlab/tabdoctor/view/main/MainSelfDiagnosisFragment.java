package com.yjrlab.tabdoctor.view.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.model.BodyPartSymptom;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;
import com.yjrlab.tabdoctor.model.UserModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.service.UserService;
import com.yjrlab.tabdoctor.view.LoginActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainSelfDiagnosisFragment extends Fragment implements View.OnClickListener, MainSelfDiagnosisAdapter.OnDiagnosisClickListener {
    private CallbackManager shareCallbackManager;
    private CallbackManager loginCallbackManager;
    private LinkedList<BodyPartSymptom> diagnosisStack = new LinkedList<>();

    private View rootView;
    private ViewGroup mLayoutNextOrPre;
    private Button mButtonPre;
    private Button mButtonNext;
    private ImageView mButtonFacebookShare;

    private ViewGroup mLayoutResult;
    private EditText mEditTextDisease;
    private ImageView mImageViewSearch;

    private String title;
    private int page;

    private MainSelfDiagnosisAdapter mSelfDiagnosisAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTextViewResult;
    private TextView mTextViewResultExplain;

    // newInstance constructor for creating fragment with arguments
    public static MainSelfDiagnosisFragment newInstance(int page, String title) {
        MainSelfDiagnosisFragment mainHomeFragment = new MainSelfDiagnosisFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        mainHomeFragment.setArguments(args);
        return mainHomeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        shareCallbackManager = CallbackManager.Factory.create();
        loginCallbackManager = CallbackManager.Factory.create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_self_diagnosis, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mSelfDiagnosisAdapter = new MainSelfDiagnosisAdapter(getContext(), null, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mSelfDiagnosisAdapter);
        mSelfDiagnosisAdapter.setOnDiagnosisClickListener(this);


        mEditTextDisease = (EditText) rootView.findViewById(R.id.editTextDisease);
        mEditTextDisease.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    rootView.findViewById(R.id.imageViewSearch).callOnClick();
                }
                return false;
            }
        });
        mImageViewSearch = (ImageView) rootView.findViewById(R.id.imageViewSearch);
        mImageViewSearch.setOnClickListener(this);

        mLayoutNextOrPre = (ViewGroup) rootView.findViewById(R.id.layoutNextOrPre);

        mButtonPre = (Button) rootView.findViewById(R.id.buttonPre);
        mButtonPre.setOnClickListener(this);
        mButtonNext = (Button) rootView.findViewById(R.id.buttonNext);
        mButtonNext.setOnClickListener(this);
        mButtonFacebookShare = (ImageView) rootView.findViewById(R.id.buttonFacebookShare);
        mButtonFacebookShare.setOnClickListener(this);

        mLayoutResult = (ViewGroup) rootView.findViewById(R.id.layoutResult);
        mTextViewResult = (TextView) rootView.findViewById(R.id.textViewResult);
        mTextViewResultExplain = (TextView) rootView.findViewById(R.id.textViewResultExplain);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewSearch:
                if (TextUtils.isEmpty(mEditTextDisease.getText())) {
                    ((MainActivity) getActivity()).hideKeyboard();
                    return;
                }
                searchDiagnosis(mEditTextDisease.getText().toString());
                ((MainActivity) getActivity()).hideKeyboard();
                break;
            case R.id.buttonPre:
                goPreDiagnosis();
                break;
            case R.id.buttonNext:
                if (selectedBodyPartSymptomModel == null) {
                    Toast.makeText(getContext(), "증상을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    goNextDiagnosis(selectedBodyPartSymptomModel);
                }
                break;
            case R.id.buttonFacebookShare:
//                mLayoutResult.buildDrawingCache();
//                Bitmap image = mLayoutResult.getDrawingCache();
                SharePhoto photo = new SharePhoto.Builder()
                        .setImageUrl(Uri.parse("http://api.tapdoctor.co.kr/assets/images/img01.png"))
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                ShareDialog dialog = new ShareDialog(this);
                dialog.show(content, ShareDialog.Mode.AUTOMATIC);
                dialog.registerCallback(shareCallbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getContext(), "페이스북 공유 취소", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("알림")
                                .setMessage("페이스북 로그인 후 가능합니다. 로그인 하시겠습니까?")
                                .setNegativeButton("취소", null)
                                .setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        initFacebookLogin();
                                    }
                                }).show();
                    }
                });
                break;
        }
    }

    private void initFacebookLogin() {

        LoginButton facebookLogin = (LoginButton) rootView.findViewById(R.id.facebookLogin);
        facebookLogin.setReadPermissions("public_profile", "email");
        facebookLogin.setFragment(this);
        facebookLogin.registerCallback(loginCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "페이스북 로그인 실패(" + error.getLocalizedMessage() + ")", Toast.LENGTH_SHORT).show();
            }
        });
        facebookLogin.callOnClick();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        shareCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private BodyPartSymptom selectedBodyPartSymptomModel;

    @Override
    public void onDiagnosisClick(BodyPartSymptom model) {
        if (!mSelfDiagnosisAdapter.isSelectMode()) {
            goNextDiagnosis(model);
        } else {
            selectedBodyPartSymptomModel = model;
        }
    }

    private void searchDiagnosis(String keyword) {
        NetworkManager.retrofit
                .create(UserService.class)
                .getSearchSymptom(PreferenceUtils.loadGender(getContext()).toGenderField(), "R", keyword)
                .enqueue(new NetworkCallback<List<GroupedBodyPartSymptomModel>>(getContext()) {
                    @Override
                    protected void onSuccess(List<GroupedBodyPartSymptomModel> content) {
                        super.onSuccess(content);
                        diagnosisStack.clear();
                        mSelfDiagnosisAdapter.setData(content, false);
                        mLayoutNextOrPre.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mLayoutResult.setVisibility(View.GONE);
                    }
                });
    }

    private void goNextDiagnosis(BodyPartSymptom model) {
        diagnosisStack.push(model);

        if (model.getBsGroupCode().equals("YA") || model.getBsGroupCode().equals("NA")) {
            setResultView(model);
        } else {
            NetworkManager.retrofit
                    .create(UserService.class)
                    .getBodyPartSymptom(PreferenceUtils.loadGender(getContext()).toGenderField(), null, null, model.getBsLevel() + 1, model.getPobId(), model.getBsId())
                    .enqueue(new NetworkCallback<List<GroupedBodyPartSymptomModel>>(getContext()) {
                        @Override
                        protected void onSuccess(List<GroupedBodyPartSymptomModel> content) {
                            super.onSuccess(content);
                            selectedBodyPartSymptomModel = null;
                            mSelfDiagnosisAdapter.setData(content, true, diagnosisStack.peek().getBsContent());
                            mLayoutNextOrPre.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mLayoutResult.setVisibility(View.GONE);
                            mButtonNext.setVisibility(View.VISIBLE);
                            mButtonFacebookShare.setVisibility(View.GONE);

                            List<BodyPartSymptom> symptoms = content.get(0).getBodyPartSymptoms();
                            if (symptoms.size() == 0) {
                                setResultView(null);
                            } else {
                                for (BodyPartSymptom symptom : symptoms) {
                                    if (symptom.getBsGroupCode().equals("A")) {
                                        setResultView(symptom);
                                        break;
                                    }
                                }
                            }

                        }
                    });
        }

    }

    private void setResultView(BodyPartSymptom symptom) {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutResult.setVisibility(View.VISIBLE);
        mButtonNext.setVisibility(View.GONE);
        if (symptom != null) {
            mTextViewResult.setText(symptom.getDdExplain());
//            mTextViewResultExplain.setText(symptom.getDdExplain());
            mButtonFacebookShare.setVisibility(View.VISIBLE);
            ArrayList<BodyPartSymptom> symptoms = new ArrayList<>(diagnosisStack);
            symptoms.add(symptom);
            postResult(symptoms);
        } else {
            mTextViewResult.setText("정상");
            mTextViewResultExplain.setText("검색된 증상이 없습니다.");
        }
    }

    private void postResult(List<BodyPartSymptom> symptoms) {
        NetworkManager.retrofit
                .create(UserService.class)
                .postSelfDiagnosises(PreferenceUtils.loadUserId(getContext()), BodyPartSymptom.toPostSelfDiagnosisQuery(symptoms))
                .enqueue(new NetworkCallback<UserModel>(getContext()) {
                    @Override
                    protected void onSuccess(UserModel content) {
                        super.onSuccess(content);

                    }
                });
    }

    public void onReselected() {
        mEditTextDisease.setText("");
        searchDiagnosis(mEditTextDisease.getText().toString());
        ((MainActivity) getActivity()).hideKeyboard();
    }

    private void goPreDiagnosis() {
        diagnosisStack.pop();
        if (diagnosisStack.size() == 0) {
            searchDiagnosis(mEditTextDisease.getText().toString());
            return;
        }

        BodyPartSymptom model = diagnosisStack.peek();
        NetworkManager.retrofit
                .create(UserService.class)
                .getBodyPartSymptom(PreferenceUtils.loadGender(getContext()).toGenderField(), null, null, model.getBsLevel() + 1, model.getPobId(), model.getBsId())
                .enqueue(new NetworkCallback<List<GroupedBodyPartSymptomModel>>(getContext()) {
                    @Override
                    protected void onSuccess(List<GroupedBodyPartSymptomModel> content) {
                        super.onSuccess(content);
                        mSelfDiagnosisAdapter.setData(content, true, diagnosisStack.peek().getBsContent());
                        selectedBodyPartSymptomModel = null;
                        mSelfDiagnosisAdapter.setData(content, true);
                        mLayoutNextOrPre.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mLayoutResult.setVisibility(View.GONE);
                        mButtonNext.setVisibility(View.VISIBLE);
                        mButtonFacebookShare.setVisibility(View.GONE);
                    }
                });
    }
}
