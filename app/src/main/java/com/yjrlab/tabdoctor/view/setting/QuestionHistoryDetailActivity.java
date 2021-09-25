package com.yjrlab.tabdoctor.view.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;
import com.yjrlab.tabdoctor.model.SelfDiagnosisModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yjrlab.tabdoctor.view.setting.QuestionHistoryAdapter.INTENT_HISTORY_LIST;
import static com.yjrlab.tabdoctor.view.setting.QuestionHistoryAdapter.INTENT_NUM;

public class QuestionHistoryDetailActivity extends TabDoctorActivity implements View.OnClickListener {

    private int num;
    private ArrayList<SelfDiagnosisModel> models;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_history_detail);

        num = getIntent().getIntExtra(INTENT_NUM, -1);
        if (num == -1) {
            Toast.makeText(getContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        models = getIntent().getParcelableArrayListExtra(INTENT_HISTORY_LIST);
        if (models == null) {
            Toast.makeText(getContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setLayout();
        setLayout(num);
    }

    private void setLayout() {
        findViewById(R.id.buttonNext).setOnClickListener(this);
        findViewById(R.id.buttonPre).setOnClickListener(this);
    }

    private void setLayout(int num) {
        SelfDiagnosisModel model = models.get(num);

        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(model.getRegDate());
            String formatDate = new SimpleDateFormat("yyyy.MM.dd").format(date);
            ((TextView) findViewById(R.id.textViewDate)).setText(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        if (model.getMsdFlag() == 0) {
//            findViewById(R.id.layoutInfo1).setVisibility(View.GONE);
//            findViewById(R.id.layoutInfo2).setVisibility(View.VISIBLE);
//            ((TextView) findViewById(R.id.textViewInfo4)).setText(model.getBsContent());
//            ((TextView) findViewById(R.id.textViewInfo5)).setText(model.getPobName());
//            ((TextView) findViewById(R.id.textViewInfo6)).setText(model.getBsGroupCode().equals("Y") ? "예" : "아니오");
//            ((TextView) findViewById(R.id.textViewInfo7)).setText("이전질문 내용");
//
//            NetworkManager.retrofit
//                    .create(UserService.class)
//                    .getBodyPartSymptomBefore(model.getPobId(), model.getBsPid())
//                    .enqueue(new NetworkCallback<List<GroupedBodyPartSymptomModel>>(getContext()) {
//                        @Override
//                        protected void onSuccess(List<GroupedBodyPartSymptomModel> content) {
//                            super.onSuccess(content);
//                            ((TextView) findViewById(R.id.textViewInfo7)).setText(content.get(0).getBodyPartSymptoms().get(0).getBsContent());
//                        }
//
//                        @Override
//                        protected void onFailure(String msg) {
//                            super.onFailure(msg);
//                            finish();
//                        }
//                    });
//        } else {
            findViewById(R.id.layoutInfo1).setVisibility(View.VISIBLE);
            findViewById(R.id.layoutInfo2).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textViewInfo1)).setText("질문내용");
            ((TextView) findViewById(R.id.textViewInfo2)).setText(model.getPobName());
            ((TextView) findViewById(R.id.textViewInfo3)).setText(model.getDdExplain());

            NetworkManager.retrofit
                    .create(UserService.class)
                    .getBodyPartSymptomFirst(model.getPobId(), model.getBsRid())
                    .enqueue(new NetworkCallback<List<GroupedBodyPartSymptomModel>>(getContext()) {
                        @Override
                        protected void onSuccess(List<GroupedBodyPartSymptomModel> content) {
                            super.onSuccess(content);
                            ((TextView) findViewById(R.id.textViewInfo1)).setText(content.get(0).getBodyPartSymptoms().get(0).getBsContent());
                        }

                        @Override
                        protected void onFailure(String msg) {
                            super.onFailure(msg);
                            finish();
                        }
                    });
//        }


        setIconVisible(num);
    }

    private void refreshBefore() {
        num--;
        setLayout(num);
        setIconVisible(num);
    }

    private void refreshAfter() {
        num++;
        setLayout(num);
        setIconVisible(num);

    }

    private void setIconVisible(int num) {
        if (num == 0) {
            findViewById(R.id.buttonPre).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.buttonPre).setVisibility(View.VISIBLE);

        }
        if (num == models.size() - 1) {
            findViewById(R.id.buttonNext).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.buttonNext).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPre:
                refreshBefore();
                Dlog.d("현재:" + num);
                break;
            case R.id.buttonNext:
                refreshAfter();
                Dlog.d("현재:" + num);
                break;
        }
    }
}
