package com.yjrlab.tabdoctor;

import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.view.LoginActivity;

public class TestActivity extends LoginActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dlog.d(FirebaseInstanceId.getInstance().getToken());
    }
}
