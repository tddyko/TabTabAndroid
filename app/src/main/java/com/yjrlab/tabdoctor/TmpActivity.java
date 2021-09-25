package com.yjrlab.tabdoctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yjrlab.tabdoctor.model.BaseModel;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;
import com.yjrlab.tabdoctor.model.GroupedSelfDiagnosisModel;
import com.yjrlab.tabdoctor.model.SelfDiagnosisModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.enums.BodyPartGenderField;
import com.yjrlab.tabdoctor.network.enums.BodyPartSearchField;
import com.yjrlab.tabdoctor.network.enums.NPorgStatus;
import com.yjrlab.tabdoctor.network.service.UserService;

import java.util.List;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public class TmpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetworkManager.retrofit
                .create(UserService.class)
                .getBodyPartSymptom(BodyPartGenderField.MAN, BodyPartSearchField.NAME, "", 2, 4, 2)
                .enqueue(new NetworkCallback<List<GroupedBodyPartSymptomModel>>(this) {
                    @Override
                    protected void onSuccess(List<GroupedBodyPartSymptomModel> content) {
                        super.onSuccess(content);
                    }
                });
    }
}
