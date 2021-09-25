package com.yjrlab.tabdoctor.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yjrlab.tabdoctor.model.ResponseModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public class NetworkCallback<C> implements Callback<ResponseModel<C>> {

    private static final String TAG = "Network#";
    private Context context;
    private ProgressDialog progressDialog;
    private int totalCount;


    public NetworkCallback(@Nullable Context context) {
        this.context = context;
        if (this.context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("알림");
            progressDialog.setMessage("잠시만 기다려주세요.");
            progressDialog.show();
        }
    }

    @Override
    public final void onResponse(@NonNull Call<ResponseModel<C>> call, @NonNull Response<ResponseModel<C>> response) {
        Log.d(TAG, "Response:" + response);

        if (response.isSuccessful()) {
            Log.d(TAG, "SUCCESS");
            ResponseModel<C> responseModel = response.body();
            if (responseModel != null) {
                totalCount = responseModel.getTotalContentsCount();
                Log.d(TAG, responseModel.toString());

                if (responseModel.isSuccess()) {
                    if (responseModel.getContent() != null) {
                        onSuccess(responseModel.getContent());
                    } else if (responseModel.getMan() != null) {
                        onSuccess(responseModel.getMan());
                    } else if (responseModel.getWoman() != null) {
                        onSuccess(responseModel.getWoman());
                    } else if (responseModel.getChildren() != null) {
                        onSuccess(responseModel.getChildren());
                    } else {
                        onSuccess(null);
                    }
                } else {
                    onFailure(responseModel.getMessage());
                }
            } else {
                onFailure("서버 에러 관리자에게 문의하세요.");
            }
        } else {
            Gson gson = new Gson();
            try {
//                Log.d(TAG, response.errorBody().string());
                ResponseModel err = gson.fromJson(response.errorBody().string(), ResponseModel.class);
                onFailure(err.getMessage());
            } catch (IOException e) {
                onFailure("서버 에러 관리자에게 문의하세요(" + e.getLocalizedMessage() + ")");
            }
        }
        onFinish();
    }

    @Override
    public final void onFailure(@NonNull Call<ResponseModel<C>> call, @NonNull Throwable t) {
        t.printStackTrace();
        onFailure("네트워크 에러 네트워크를 확인해주세요.(" + t.getLocalizedMessage() + ")");
        onFinish();
    }

    protected void onSuccess(C content) {
        if (content != null) {
            Log.d(TAG, "content:" + content.toString());
        }
    }

    protected void onFailure(String msg) {
        if (msg == null) {
            msg = "알 수 없는 에러 발생";
        }
        Log.d(TAG, msg);
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    protected void onFinish() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
