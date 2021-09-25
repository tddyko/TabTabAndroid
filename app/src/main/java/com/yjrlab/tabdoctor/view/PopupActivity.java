package com.yjrlab.tabdoctor.view;

import android.app.NotificationManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.model.BodyPartSymptom;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;
import com.yjrlab.tabdoctor.model.PushQuestionModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.service.PushService;
import com.yjrlab.tabdoctor.network.service.UserService;
import com.yjrlab.tabdoctor.service.TabDoctorFCMService;

import java.util.List;


public class PopupActivity extends TabDoctorActivity implements View.OnClickListener {
    public static final String INTENT_QUESTION_MODEL = "Question";

    private PushQuestionModel pushQuestionModel;
    private BodyPartSymptom positiveBodyPartSymptom;
    private BodyPartSymptom negativeBodyPartSymptom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_popup);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        if (getActionBar() != null)
            getActionBar().hide();
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        onNotification();
        findViewById(R.id.buttonClose).setOnClickListener(this);
        findViewById(R.id.buttonOk).setOnClickListener(this);
        findViewById(R.id.buttonNo).setOnClickListener(this);
        findViewById(R.id.buttonConfirm).setOnClickListener(this);
        final TextView textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);

        pushQuestionModel = getIntent().getParcelableExtra(INTENT_QUESTION_MODEL);


        if (pushQuestionModel == null) {
            Toast.makeText(getContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        textViewQuestion.setText(pushQuestionModel.getBsContent());

        if (pushQuestionModel.getBsGroupCode().contains("A")) {
            textViewQuestion.setText(pushQuestionModel.getDdExplain());
            setModeConfirm();
            return;
        }


        NetworkManager.retrofit
                .create(UserService.class)
                .getBodyPartSymptom(PreferenceUtils.loadGender(getContext()).toGenderField(), null, null, pushQuestionModel.getBsLevel() + 1, pushQuestionModel.getPobId(), pushQuestionModel.getBsId())
                .enqueue(new NetworkCallback<List<GroupedBodyPartSymptomModel>>(getContext()) {
                    @Override
                    protected void onSuccess(List<GroupedBodyPartSymptomModel> content) {
                        super.onSuccess(content);
                        for (BodyPartSymptom symptom : content.get(0).getBodyPartSymptoms()) {
                            if (symptom.getBsGroupCode().contains("Y")) {
                                positiveBodyPartSymptom = symptom;
                            } else if (symptom.getBsGroupCode().contains("N")) {
                                negativeBodyPartSymptom = symptom;
                            }
                        }
                    }
                });

    }

    private void setModeConfirm() {
        findViewById(R.id.buttonOk).setVisibility(View.GONE);
        findViewById(R.id.buttonNo).setVisibility(View.GONE);
        findViewById(R.id.buttonConfirm).setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonClose:
                finish();
                break;
            case R.id.buttonNo:
                postAnswer(negativeBodyPartSymptom);
                break;
            case R.id.buttonOk:
                postAnswer(positiveBodyPartSymptom);
                break;
            case R.id.buttonConfirm:
                finish();
                break;
        }
    }

    private void onNotification() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private void cancelNotification() {
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(TabDoctorFCMService.NOTIFICATION_ID);
    }

    public void postAnswer(BodyPartSymptom symptom) {
        String data;
        data = symptom.toPostPushQuery(pushQuestionModel.getMprId());

        NetworkManager.retrofit
                .create(PushService.class)
                .postQuestion(PreferenceUtils.loadUserId(getContext()), data)
                .enqueue(new NetworkCallback<PushQuestionModel>(getContext()) {
                    @Override
                    protected void onSuccess(PushQuestionModel content) {
                        super.onSuccess(content);
                        cancelNotification();
                        finish();
                    }
                });
    }
}
