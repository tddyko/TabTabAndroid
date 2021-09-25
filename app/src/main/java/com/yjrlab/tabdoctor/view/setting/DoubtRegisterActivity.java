package com.yjrlab.tabdoctor.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;
import com.yjrlab.tabdoctor.dialog.DialogSelectSymptoms;
import com.yjrlab.tabdoctor.libs.CheckableButton;
import com.yjrlab.tabdoctor.libs.CustomActionBar;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.model.BodyPartSymptom;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;
import com.yjrlab.tabdoctor.model.UserModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.enums.BodyPartGenderField;
import com.yjrlab.tabdoctor.network.service.MainService;

import java.util.ArrayList;
import java.util.List;

import static com.yjrlab.tabdoctor.view.SignUpActivity.INTENT_SIGNUP;
import static com.yjrlab.tabdoctor.view.SignUpActivity.INTENT_USERMODEL;

public class DoubtRegisterActivity extends TabDoctorActivity implements View.OnClickListener {
    private int[] checkableButtons = {R.id.checkableButtonAdult1, R.id.checkableButtonAdult2, R.id.checkableButtonAdult3,
            R.id.checkableButtonAdult4, R.id.checkableButtonAdult5, R.id.checkableButtonAdult6, R.id.checkableButtonAdult7, R.id.checkableButtonBaby1};
    public static final String INTENT_CHECKED_TAGS = "tags";
    public static final String INTENT_CHECKED_SYMPTOMS = "symptoms";

    private ViewGroup layoutAdult;
    private ImageView imageView;
    private ViewGroup layoutBaby;

    private Button buttonReg;

    private CheckableButton checkableButtonAdult1;
    private CheckableButton checkableButtonAdult2;
    private CheckableButton checkableButtonAdult3;
    private CheckableButton checkableButtonAdult4;
    private CheckableButton checkableButtonAdult5;
    private CheckableButton checkableButtonAdult6;
    private CheckableButton checkableButtonAdult7;
    private CheckableButton checkableButtonBaby1;

    private List<GroupedBodyPartSymptomModel> data;
    private BodyPartSymptomList selectedData;
    private boolean isFirstRegister;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doubt);
        selectedData = new BodyPartSymptomList();

        //첫 로그인 시 커스텀 액션바 UI
        isFirstRegister = getIntent().getBooleanExtra(INTENT_SIGNUP, false);
        if (isFirstRegister) {
            CustomActionBar customActionBar = (CustomActionBar) findViewById(R.id.customActionBar);
            customActionBar.setHomeVisible(false);
            customActionBar.setIconVisible(false);
        }

        userModel = getIntent().getParcelableExtra(INTENT_USERMODEL);


        setLayout();
        setType();

    }

    private void setType() {
        ShowTypeEnum type;
        //최초 등록 시 userModel을 intent로 가져옴
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

        NetworkManager.retrofit
                .create(MainService.class)
                .getBodyPartSymptomWithSex(BodyPartGenderField.parse(type))
                .enqueue(new NetworkCallback<List<GroupedBodyPartSymptomModel>>(getContext()) {
                    @Override
                    protected void onSuccess(List<GroupedBodyPartSymptomModel> content) {
                        super.onSuccess(content);
                        data = content;
                    }

                    @Override
                    protected void onFailure(String msg) {
                        super.onFailure(msg);
                        finish();
                    }
                });
    }

    private void setLayout() {
        layoutAdult = (ViewGroup) findViewById(R.id.layoutAdult);
        layoutBaby = (ViewGroup) findViewById(R.id.layoutBaby);
        imageView = (ImageView) findViewById(R.id.imageView);

        for (int button : checkableButtons) {
            findViewById(button).setOnClickListener(this);
        }

        buttonReg = (Button) findViewById(R.id.buttonReg);
        buttonReg.setOnClickListener(this);
        checkableButtonBaby1 = (CheckableButton) findViewById(R.id.checkableButtonBaby1);
        checkableButtonAdult1 = (CheckableButton) findViewById(R.id.checkableButtonAdult1);
        checkableButtonAdult2 = (CheckableButton) findViewById(R.id.checkableButtonAdult2);
        checkableButtonAdult3 = (CheckableButton) findViewById(R.id.checkableButtonAdult3);
        checkableButtonAdult4 = (CheckableButton) findViewById(R.id.checkableButtonAdult4);
        checkableButtonAdult5 = (CheckableButton) findViewById(R.id.checkableButtonAdult5);
        checkableButtonAdult6 = (CheckableButton) findViewById(R.id.checkableButtonAdult6);
        checkableButtonAdult7 = (CheckableButton) findViewById(R.id.checkableButtonAdult7);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonReg:
                if (selectedData.all == null || selectedData.all.size() == 0) {
                    Toast.makeText(getContext(), "한개이상의 의심부위를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO: 2017. 6. 7. Dialog로 데이터 전송 부분
                Intent intent = new Intent(DoubtRegisterActivity.this, DoubtResultActivity.class);
                intent.putIntegerArrayListExtra(INTENT_CHECKED_TAGS, getCheckedData());
                intent.putExtra(INTENT_CHECKED_SYMPTOMS, selectedData);
                intent.putExtra(INTENT_SIGNUP, isFirstRegister);
                if (userModel != null) {
                    intent.putExtra(INTENT_USERMODEL, userModel);
                }
                startActivity(intent);
                finish();
                break;

            case R.id.checkableButtonAdult3:
                if (checkableButtonAdult3.isChecked()) {
                    setCheckButtons(true);
                } else {
                    setCheckButtons(false);
                }
                break;
            case R.id.checkableButtonAdult4:
                if (checkableButtonAdult4.isChecked()) {
                    setCheckButtons(true);
                } else {
                    setCheckButtons(false);
                }
                break;
            case R.id.checkableButtonAdult7:
                if (checkableButtonAdult7.isChecked()) {
                    setCheckButtons(true);
                } else {
                    setCheckButtons(false);
                }
                break;

        }

        for (int button : checkableButtons) {
            if (v.getId() == button) {
                final CheckableButton checkableButton = (CheckableButton) findViewById(button);

                if (checkableButton.isChecked()) {
                    for (GroupedBodyPartSymptomModel model : data) {
                        if ((model.getPobId() == Integer.parseInt(checkableButton.getTag().toString()))) {
                            //선택된 BodyPartSymptom을 selectedData에 저장한다.

                            DialogSelectSymptoms dialogSelectSymptoms = new DialogSelectSymptoms(getContext(), model.getPobName(), model.getBodyPartSymptoms());
                            dialogSelectSymptoms.setCancelable(false);
                            dialogSelectSymptoms.show();
                            dialogSelectSymptoms.setDialogResult(new DialogSelectSymptoms.OnMyDialogResultJob() {
                                @Override
                                public void finish(List<BodyPartSymptom> result) {
                                    if (result == null) {
                                        if (checkableButton.equals(checkableButtonAdult3) || checkableButton.equals(checkableButtonAdult4) || checkableButton.equals(checkableButtonAdult7)) {
                                            setCheckButtons(false);
                                        } else {
                                            checkableButton.setChecked(false);
                                        }

                                    } else {
                                        selectedData.add(result);
                                        Dlog.d(result.toString());
                                    }
                                }
                            });
                            break;
                        }
                    }
                } else {
                    for (int i = selectedData.all.size() - 1; i >= 0; i--) {
                        BodyPartSymptom symptom = selectedData.all.get(i);
                        if (symptom.getPobId() == Integer.parseInt(checkableButton.getTag().toString())) {
                            selectedData.all.remove(i);
                        }
                    }
                }

                break;
            }
        }


    }

    public void setCheckButtons(boolean b) {
        checkableButtonAdult3.setChecked(b);
        checkableButtonAdult4.setChecked(b);
        checkableButtonAdult7.setChecked(b);
    }

    private ArrayList<Integer> getCheckedData() {
        ArrayList<Integer> list = new ArrayList<>();

        for (int button : checkableButtons) {
            CheckableButton checkableButton = (CheckableButton) findViewById(button);
            if (checkableButton.isChecked()) {
                list.add(Integer.parseInt(checkableButton.getTag().toString()));
            }
        }
        Dlog.d(list.toString() + "보낸");
        return list;
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
