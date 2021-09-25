package com.yjrlab.tabdoctor.service;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.model.UserModel;
import com.yjrlab.tabdoctor.network.NetworkCallback;
import com.yjrlab.tabdoctor.network.NetworkManager;
import com.yjrlab.tabdoctor.network.service.UserService;

/**
 * Created by jongrakmoon on 2017. 6. 12..
 */

public class TabDoctorFCMInstanceService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        String email = PreferenceUtils.loadEmail(this);
        if (!TextUtils.isEmpty(email)) {
            NetworkManager.retrofit
                    .create(UserService.class)
                    .getInfo(email, refreshedToken)
                    .enqueue(new NetworkCallback<UserModel>(null));
        }

    }
}
