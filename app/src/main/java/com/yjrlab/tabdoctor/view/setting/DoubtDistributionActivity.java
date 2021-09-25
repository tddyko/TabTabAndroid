package com.yjrlab.tabdoctor.view.setting;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;
import com.yjrlab.tabdoctor.libs.CheckableButton;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.model.DoubtModel;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;
import com.yjrlab.tabdoctor.model.GroupedDoubtModel;
import com.yjrlab.tabdoctor.model.GroupedSelfDiagnosisModel;
import com.yjrlab.tabdoctor.model.SelfDiagnosisModel;
import com.yjrlab.tabdoctor.model.UserDoubtModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.service.MainService;
import com.yjrlab.tabdoctor.network.service.UserService;

import java.util.List;

public class DoubtDistributionActivity extends TabDoctorActivity {
    private int[] checkableButtons = {R.id.checkableButtonAdult1, R.id.checkableButtonAdult2, R.id.checkableButtonAdult3,
            R.id.checkableButtonAdult4, R.id.checkableButtonAdult5, R.id.checkableButtonAdult6, R.id.checkableButtonAdult7, R.id.checkableButtonBaby1};
    private int[] imageButtons = {R.drawable.icon_yellow_circle, R.drawable.icon_orange_circle, R.drawable.icon_dahong_circle, R.drawable.icon_red_circle};


    private ViewGroup layoutAdult;
    private ViewGroup layoutBaby;
    private ImageView imageView;
    private List<GroupedBodyPartSymptomModel> data;

    private CheckableButton checkableButtonAdult1;
    private CheckableButton checkableButtonAdult2;
    private CheckableButton checkableButtonAdult3;
    private CheckableButton checkableButtonAdult4;
    private CheckableButton checkableButtonAdult5;
    private CheckableButton checkableButtonAdult6;
    private CheckableButton checkableButtonAdult7;
    private CheckableButton checkableButtonBaby1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_distribution);
        setLayout();
        setType();
        getDistributionData();
    }

    private void getDistributionData() {
        NetworkManager.retrofit
                .create(UserService.class)
                .getDoubtDistribute(PreferenceUtils.loadUserId(getContext()))
                .enqueue(new NetworkCallback<List<SelfDiagnosisModel>>(this) {
                    @Override
                    protected void onSuccess(List<SelfDiagnosisModel> content) {
                        super.onSuccess(content);

                        GroupedSelfDiagnosisModel model = GroupedSelfDiagnosisModel.groupBySelfDiagnosisModels(content);
                        for (int button : checkableButtons) {
                            CheckableButton checkableButton = (CheckableButton) findViewById(button);
                            Object tagObj = checkableButton.getTag();
                            if (tagObj != null) {
                                int tag = Integer.parseInt(tagObj.toString());

                                if (model.get(tag) != null) {
                                    int symptomCount = model.get(tag).size();
                                    if (symptomCount < 9) {
                                        checkableButton.setBackgroundResource(imageButtons[0]);
                                    } else if (symptomCount < 17) {
                                        checkableButton.setBackgroundResource(imageButtons[1]);
                                    } else if (symptomCount < 23) {
                                        checkableButton.setBackgroundResource(imageButtons[2]);
                                    } else {
                                        checkableButton.setBackgroundResource(imageButtons[3]);
                                    }

                                } else {
                                    checkableButton.setVisibility(View.GONE);
                                }
                            } else {
                                checkableButton.setVisibility(View.GONE);
                            }
                        }

                    }

                    @Override
                    protected void onFailure(String msg) {
                        super.onFailure(msg);
                        finish();
                    }
                });

    }

    private void setType() {
        ShowTypeEnum type = PreferenceUtils.loadGender(getContext());

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

        checkableButtonAdult1 = (CheckableButton) findViewById(R.id.checkableButtonAdult1);
        checkableButtonAdult2 = (CheckableButton) findViewById(R.id.checkableButtonAdult2);
        checkableButtonAdult3 = (CheckableButton) findViewById(R.id.checkableButtonAdult3);
        checkableButtonAdult4 = (CheckableButton) findViewById(R.id.checkableButtonAdult4);
        checkableButtonAdult5 = (CheckableButton) findViewById(R.id.checkableButtonAdult5);
        checkableButtonAdult6 = (CheckableButton) findViewById(R.id.checkableButtonAdult6);
        checkableButtonAdult7 = (CheckableButton) findViewById(R.id.checkableButtonAdult7);
        checkableButtonBaby1 = (CheckableButton) findViewById(R.id.checkableButtonBaby1);


    }


}
