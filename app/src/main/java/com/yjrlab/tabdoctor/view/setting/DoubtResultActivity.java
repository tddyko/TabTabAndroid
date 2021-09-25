package com.yjrlab.tabdoctor.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;
import com.yjrlab.tabdoctor.libs.CheckableButton;
import com.yjrlab.tabdoctor.libs.CustomActionBar;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.model.BaseModel;
import com.yjrlab.tabdoctor.model.BodyPartSymptom;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;
import com.yjrlab.tabdoctor.model.UserModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.service.MainService;
import com.yjrlab.tabdoctor.network.service.UserService;
import com.yjrlab.tabdoctor.view.SignUpActivity;
import com.yjrlab.tabdoctor.view.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static com.yjrlab.tabdoctor.view.SignUpActivity.INTENT_SIGNUP;
import static com.yjrlab.tabdoctor.view.SignUpActivity.INTENT_USERMODEL;
import static com.yjrlab.tabdoctor.view.setting.DoubtRegisterActivity.INTENT_CHECKED_SYMPTOMS;
import static com.yjrlab.tabdoctor.view.setting.DoubtRegisterActivity.INTENT_CHECKED_TAGS;

public class DoubtResultActivity extends TabDoctorActivity implements View.OnClickListener {
    private int[] checkableButtons = {R.id.checkableButtonAdult1, R.id.checkableButtonAdult2, R.id.checkableButtonAdult3,
            R.id.checkableButtonAdult4, R.id.checkableButtonAdult5, R.id.checkableButtonAdult6, R.id.checkableButtonAdult7, R.id.checkableButtonBaby1};
    private ViewGroup layoutAdult;
    private ImageView imageView;
    private ViewGroup layoutBaby;
    private CheckableButton checkableButtonAdult1;
    private CheckableButton checkableButtonAdult2;
    private CheckableButton checkableButtonAdult3;
    private CheckableButton checkableButtonAdult4;
    private CheckableButton checkableButtonAdult5;
    private CheckableButton checkableButtonAdult6;
    private CheckableButton checkableButtonAdult7;
    private CheckableButton checkableButtonBaby1;

    private ArrayList<Integer> tagList;
    private BodyPartSymptomList symptomsList;
    private RecyclerView recyclerView;
    private boolean isFirstRegister;
    private UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_doubt);

        tagList = getIntent().getIntegerArrayListExtra(INTENT_CHECKED_TAGS);
        if (tagList == null) {
            Toast.makeText(getContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        symptomsList = getIntent().getParcelableExtra(INTENT_CHECKED_SYMPTOMS);
        if (symptomsList == null) {
            Toast.makeText(getContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //첫 로그인 시 커스텀 액션바 UI
        isFirstRegister = getIntent().getBooleanExtra(INTENT_SIGNUP, false);
        if (isFirstRegister) {
            CustomActionBar customActionBar = (CustomActionBar) findViewById(R.id.customActionBar);
            customActionBar.setHomeVisible(false);
            customActionBar.setIconVisible(false);
        }

        userModel = getIntent().getParcelableExtra(INTENT_USERMODEL);
        setLayout();

    }

    private void setType() {
        ShowTypeEnum type;
        if (userModel == null) {
            type = PreferenceUtils.loadGender(getContext());
        } else {
            type = userModel.getShowTypeEnum();
        }

        if (type.equals(ShowTypeEnum.BABY)) {
            layoutBaby.setVisibility(View.VISIBLE);
            layoutAdult.setVisibility(View.GONE);
            checkableButtonBaby1.setTag(13);
        } else {
            layoutAdult.setVisibility(View.VISIBLE);
            layoutBaby.setVisibility(View.GONE);
            if (type.equals(ShowTypeEnum.MAN)) {
                checkableButtonAdult1.setTag(1);
                checkableButtonAdult2.setTag(4);
                checkableButtonAdult3.setTag(6);
                checkableButtonAdult4.setTag(6);
                checkableButtonAdult7.setTag(6);
                checkableButtonAdult5.setTag(5);
                checkableButtonAdult6.setTag(7);

                imageView.setImageResource(R.drawable.img_man);
            } else if (type.equals(ShowTypeEnum.WOMAN)) {
                imageView.setImageResource(R.drawable.img_woman);
                checkableButtonAdult1.setTag(8);
                checkableButtonAdult2.setTag(9);
                checkableButtonAdult3.setTag(11);
                checkableButtonAdult4.setTag(11);
                checkableButtonAdult7.setTag(11);
                checkableButtonAdult5.setTag(10);
                checkableButtonAdult6.setTag(12);
            }
        }

    }

    private void setLayout() {
        layoutAdult = (ViewGroup) findViewById(R.id.layoutAdult);
        layoutBaby = (ViewGroup) findViewById(R.id.layoutBaby);
        imageView = (ImageView) findViewById(R.id.imageView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        findViewById(R.id.buttonReg).setOnClickListener(this);

        checkableButtonAdult1 = (CheckableButton) findViewById(R.id.checkableButtonAdult1);
        checkableButtonAdult2 = (CheckableButton) findViewById(R.id.checkableButtonAdult2);
        checkableButtonAdult3 = (CheckableButton) findViewById(R.id.checkableButtonAdult3);
        checkableButtonAdult4 = (CheckableButton) findViewById(R.id.checkableButtonAdult4);
        checkableButtonAdult5 = (CheckableButton) findViewById(R.id.checkableButtonAdult5);
        checkableButtonAdult6 = (CheckableButton) findViewById(R.id.checkableButtonAdult6);
        checkableButtonAdult7 = (CheckableButton) findViewById(R.id.checkableButtonAdult7);
        checkableButtonBaby1 = (CheckableButton) findViewById(R.id.checkableButtonBaby1);

        //보여줄 타입 및 태그 지정
        setType();

        if (tagList != null) {

            for (int button : checkableButtons) {
                CheckableButton checkableButton = (CheckableButton) findViewById(button);
                if (checkableButton.getTag() != null && tagList.contains(Integer.parseInt(checkableButton.getTag().toString()))) {
                    checkableButton.setVisibility(View.VISIBLE);
                    checkableButton.setChecked(true);
                    checkableButton.setEnabled(false);
                } else {
                    checkableButton.setVisibility(View.INVISIBLE);
                }
            }
        }

        if (symptomsList.all != null) {
            Dlog.d("받은");
            List<GroupedBodyPartSymptomModel> grouped = GroupedBodyPartSymptomModel.groupByBodyPartSymptom(symptomsList.all);
            recyclerView.setAdapter(new DoubtResultAdapter(getContext(), grouped));

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonReg:
                Dlog.d(BodyPartSymptom.toPostDoubtQuery(symptomsList.all));

                if (userModel != null) {
                    NetworkManager.retrofit
                            .create(UserService.class)
                            .signup(userModel.getQueryMap())
                            .enqueue(new NetworkCallback<UserModel>(getContext()) {
                                @Override
                                protected void onSuccess(UserModel content) {
                                    super.onSuccess(content);
                                    PreferenceUtils.saveEmail(getContext(), userModel.getEmail());
                                    PreferenceUtils.saveGender(getContext(), userModel.getShowTypeEnum());
                                    PreferenceUtils.saveUserId(getContext(), content.getId());

                                    //최초 정보 업로드
                                    postFirstResult(content.getId());

                                }

                                @Override
                                protected void onFailure(String msg) {
                                    super.onFailure(msg);
                                }
                            });
                } else {
                    postResult(PreferenceUtils.loadUserId(getContext()));
                }


                break;
        }
    }

    private void postFirstResult(long userId) {
        NetworkManager.retrofit
                .create(MainService.class)
                .postHealthLoginDoubt(userId, BodyPartSymptom.toPostDoubtQuery(symptomsList.all))
                .enqueue(new NetworkCallback<BaseModel>(getContext()) {
                    @Override
                    protected void onSuccess(BaseModel content) {
                        super.onSuccess(content);
                        finish();
                    }


                });

    }

    private void postResult(long userId) {
        NetworkManager.retrofit
                .create(MainService.class)
                .postHealthDoubt(userId, BodyPartSymptom.toPostDoubtQuery(symptomsList.all))
                .enqueue(new NetworkCallback<BaseModel>(getContext()) {
                    @Override
                    protected void onSuccess(BaseModel content) {
                        super.onSuccess(content);
                        finish();
                    }


                });

    }

    @Override
    public void onBackPressed() {
        if (isFirstRegister) {
            Toast.makeText(getContext(), "등록을 먼저 진행하세요.", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}
