package com.yjrlab.tabdoctor.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;
import com.yjrlab.tabdoctor.dialog.DialogDefault;
import com.yjrlab.tabdoctor.dialog.DialogSelectAge;
import com.yjrlab.tabdoctor.dialog.DialogSelectJob;
import com.yjrlab.tabdoctor.dialog.DialogTermsOfUse;
import com.yjrlab.tabdoctor.dialog.DialogWelcome;
import com.yjrlab.tabdoctor.libs.CheckableButton;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.view.main.MainActivity;
import com.yjrlab.tabdoctor.model.UserModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.service.UserService;
import com.yjrlab.tabdoctor.view.setting.DoubtRegisterActivity;

public class SignUpActivity extends TabDoctorActivity implements View.OnClickListener {
    public static final String INTENT_EMAIL = "email";
    public static final String INTENT_NAME = "name";
    public static final String INTENT_LOGIN_REFER = "refer";
    public static final String INTENT_SIGNUP = "signup";
    public static final String INTENT_USERMODEL = "usermodel";


    private TextView textViewEmail;
    private CheckableButton checkableButtonAll;
    private CheckableButton checkableButton1;
    private CheckableButton checkableButton2;
    private CheckableButton checkableButton3;
    private CheckableButton radioButtonMan;
    private CheckableButton radioButtonWoman;

    private LinearLayout layout1;
    private LinearLayout layout2;
    private EditText editTextAge;
    private EditText editTextJob;

    private String loginRefer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setLayout();

        Dlog.d("회언가입");
        Intent intent = getIntent();
        String email = intent.getStringExtra(INTENT_EMAIL);
        loginRefer = intent.getStringExtra(INTENT_LOGIN_REFER);
        if (email == null || loginRefer == null) {
            Toast.makeText(getContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        textViewEmail.setText(email);
        DialogWelcome dialogWelcome = new DialogWelcome(this);
        dialogWelcome.show();

    }

    private void setLayout() {
        findViewById(R.id.buttonOk).setOnClickListener(this);
        findViewById(R.id.buttonOk2).setOnClickListener(this);
        findViewById(R.id.checkableButtonAll).setOnClickListener(this);
        findViewById(R.id.textViewTerm1).setOnClickListener(this);
        findViewById(R.id.textViewTerm2).setOnClickListener(this);
        findViewById(R.id.textViewTerm3).setOnClickListener(this);

        layout1 = (LinearLayout) findViewById(R.id.layout_1);
        layout2 = (LinearLayout) findViewById(R.id.layout_2);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        radioButtonMan = (CheckableButton) findViewById(R.id.radioButtonMan);
        radioButtonMan.setOnClickListener(this);
        radioButtonWoman = (CheckableButton) findViewById(R.id.radioButtonWoman);
        radioButtonWoman.setOnClickListener(this);
        radioButtonMan.setChecked(true);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextAge.setOnClickListener(this);
        editTextJob = (EditText) findViewById(R.id.editTextJob);
        editTextJob.setOnClickListener(this);
        checkableButtonAll = (CheckableButton) findViewById(R.id.checkableButtonAll);
        checkableButton1 = (CheckableButton) findViewById(R.id.checkableButton1);
        checkableButton2 = (CheckableButton) findViewById(R.id.checkableButton2);
        checkableButton3 = (CheckableButton) findViewById(R.id.checkableButton3);

    }

    @Override
    public void onClick(View v) {
        DialogTermsOfUse dialogTermsOfUse;
        DialogDefault dialogDefault;
        switch (v.getId()) {

            //layout1
            case R.id.checkableButtonAll:
                if (checkableButtonAll.isChecked()) {
                    checkableButton1.setChecked(true);
                    checkableButton2.setChecked(true);
                    checkableButton3.setChecked(true);
                } else {
                    checkableButton1.setChecked(false);
                    checkableButton2.setChecked(false);
                    checkableButton3.setChecked(false);
                }

                break;

            case R.id.buttonOk:
                if (!checkableButton1.isChecked() || !checkableButton2.isChecked()) {
                    dialogDefault = new DialogDefault(getContext(), "약관 동의를 전부");
                    dialogDefault.show();
                    return;
                }
                //layout1 -> layout2
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                break;

            case R.id.buttonOk2:
                String email = textViewEmail.getText().toString();
                String job = editTextJob.getText().toString();
                String age = editTextAge.getText().toString();

                if (job.equals("")) {
                    dialogDefault = new DialogDefault(getContext(), "직업을");
                    dialogDefault.show();
                    return;
                }

                if (age.equals("")) {
                    dialogDefault = new DialogDefault(getContext(), "나이를");
                    dialogDefault.show();
                    return;
                }


                final UserModel userModel = new UserModel();
                userModel.setEmail(email);
                userModel.setJob(job);
                userModel.setAgeWithStr(age);
                userModel.setSnsRefer(loginRefer);
                userModel.setSex(radioButtonMan.isChecked() ? UserModel.GENDER_MAN : UserModel.GENDER_WOMAN);
                userModel.setToken(FirebaseInstanceId.getInstance().getToken());
                userModel.setMemOs("A");

                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                Intent intent = new Intent(SignUpActivity.this,DoubtRegisterActivity.class);
                intent.putExtra(INTENT_SIGNUP,true);
                intent.putExtra(INTENT_USERMODEL,userModel);
                startActivity(intent);
                finish();


                break;

            //layout2
            case R.id.editTextAge:
                DialogSelectAge dialogSelectAge = new DialogSelectAge(SignUpActivity.this);
                dialogSelectAge.show();
                dialogSelectAge.setDialogResult(new DialogSelectAge.OnMyDialogResult() {
                    public void finish(String result) {
                        Dlog.d(result + "");
                        ((EditText) findViewById(R.id.editTextAge)).setText(result);
                    }
                });
                break;

            case R.id.editTextJob:
                DialogSelectJob dialogSelectJob = new DialogSelectJob(SignUpActivity.this);
                dialogSelectJob.show();
                dialogSelectJob.setDialogResult(new DialogSelectJob.OnMyDialogResultJob() {
                    @Override
                    public void finish(String result) {
                        Dlog.d(result + "");
                        ((EditText) findViewById(R.id.editTextJob)).setText(result);

                    }
                });
                break;


            case R.id.radioButtonMan:
                radioButtonMan.setChecked(true);
                radioButtonWoman.setChecked(false);
                break;
            case R.id.radioButtonWoman:
                radioButtonMan.setChecked(false);
                radioButtonWoman.setChecked(true);
                break;


            case R.id.textViewTerm1:
                dialogTermsOfUse = new DialogTermsOfUse(getContext(), "이용약관", getString(R.string.terms1Desc));
                dialogTermsOfUse.show();
                break;
            case R.id.textViewTerm2:
                dialogTermsOfUse = new DialogTermsOfUse(getContext(), "개인정보 취급방침", getString(R.string.terms2Desc));
                dialogTermsOfUse.show();
                break;
            case R.id.textViewTerm3:
                dialogTermsOfUse = new DialogTermsOfUse(getContext(), "개인정보의 제3자 제공", getString(R.string.terms3Desc));
                dialogTermsOfUse.show();
                break;
        }

    }
}
