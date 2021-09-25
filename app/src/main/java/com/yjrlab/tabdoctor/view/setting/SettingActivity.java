package com.yjrlab.tabdoctor.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;

public class SettingActivity extends TabDoctorActivity implements View.OnClickListener {
    public SettingActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        instance = this;
        setLayout();
    }


    private void setLayout() {
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                startActivity(new Intent(SettingActivity.this, DoubtRegisterActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(SettingActivity.this, QuestionHistoryActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(SettingActivity.this, DoubtDistributionActivity.class));
                break;
            case R.id.button4:
                // TODO: 2017. 6. 12. 제3자동의 정보 가져오기
                boolean didAgree = false;
                if (didAgree) {
                    Toast.makeText(getContext(),"이미 동의하셨습니다.",Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(SettingActivity.this, NPorganizationActivity.class));
                }
                break;

        }

    }
}
