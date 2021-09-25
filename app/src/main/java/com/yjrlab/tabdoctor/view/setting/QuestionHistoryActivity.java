package com.yjrlab.tabdoctor.view.setting;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;
import com.yjrlab.tabdoctor.dialog.DialogNumberPicker;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.model.SelfDiagnosisModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class QuestionHistoryActivity extends TabDoctorActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private QuestionHistoryAdapter questionHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_history);
        setLayout();
    }

    private void refreshData(String startDate, String lastDate) {

        NetworkManager.retrofit
                .create(UserService.class)
                .getSelfDiagnosises(PreferenceUtils.loadUserId(getContext()), startDate, lastDate)
                .enqueue(new NetworkCallback<List<SelfDiagnosisModel>>(getContext()) {
                    @Override
                    protected void onSuccess(List<SelfDiagnosisModel> content) {
                        super.onSuccess(content);
                        questionHistoryAdapter.setOffset(content);
                    }
                });

    }

    private void setLayout() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE,1);
        String lastDate = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        calendar.add(Calendar.MONTH, -3);
        String startDate = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());

        Dlog.d(startDate);
        Dlog.d(lastDate);


        questionHistoryAdapter = new QuestionHistoryAdapter(getContext(), null);
        recyclerView.setAdapter(questionHistoryAdapter);
        findViewById(R.id.buttonCalendar).setOnClickListener(this);

        refreshData(startDate, lastDate);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCalendar:
                DialogNumberPicker dialog = new DialogNumberPicker(getContext());
                dialog.setOnFinishResultListner(new DialogNumberPicker.OnFinishResultListener() {
                    @Override
                    public void onFinish(int startDate, int lastDate) {
                        refreshData(startDate + "", lastDate + "");
                    }

                });
                dialog.show();
                break;
        }

    }


}
