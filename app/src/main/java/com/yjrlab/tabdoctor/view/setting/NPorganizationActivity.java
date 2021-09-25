package com.yjrlab.tabdoctor.view.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.TabDoctorActivity;
import com.yjrlab.tabdoctor.dialog.DialogDefault;
import com.yjrlab.tabdoctor.libs.CheckableButton;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.model.BaseModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.enums.NPorgStatus;
import com.yjrlab.tabdoctor.network.service.UserService;

import static android.view.View.GONE;

public class NPorganizationActivity extends TabDoctorActivity implements View.OnClickListener {

    private CheckableButton checkableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_np_organization);
        setLayout();

    }

    private void setLayout() {
        checkableButton = (CheckableButton) findViewById(R.id.checkableButton);
        findViewById(R.id.buttonOk).setOnClickListener(this);

        //이미 동의했을 때의 UI
        if(PreferenceUtils.loadNporg(getContext())){
            Dlog.d("이미 동의");
            checkableButton.setVisibility(View.INVISIBLE);
            findViewById(R.id.buttonOk).setVisibility(View.INVISIBLE);
            ((TextView)findViewById(R.id.textView)).setText("에 이미 동의하셨습니다.");
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonOk:
                if (!checkableButton.isChecked()) {
                    DialogDefault d = new DialogDefault(getContext(), "개인정보 제3자 제공을");
                    d.show();
                    return;
                } else {
                    NetworkManager.retrofit
                            .create(UserService.class)
                            .postNporgAgree(PreferenceUtils.loadUserId(getContext()), NPorgStatus.AGREE)
                            .enqueue(new NetworkCallback<BaseModel>(getContext()) {
                                @Override
                                protected void onSuccess(BaseModel content) {
                                    super.onSuccess(content);
                                    PreferenceUtils.saveNporg(getContext(),NPorgStatus.AGREE);
                                    Toast.makeText(getContext(), "비영리 단체 연동이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
                // TODO: 2017. 6. 10. 서버와 연동
                break;
        }

    }
}
