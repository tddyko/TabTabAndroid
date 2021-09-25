package com.yjrlab.tabdoctor.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.PreferenceUtils;
import com.yjrlab.tabdoctor.view.LoginActivity;


public class DialogLogout extends DialogBase implements View.OnClickListener {
    private final String text;
    private final Context context;
    private OnMyDialogResult mDialogResult;

    public DialogLogout(@NonNull Context context, @NonNull String text) {
        super(context);
        this.context = context;
        this.text = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_logout);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ((TextView) findViewById(R.id.textView)).setText(text);
        findViewById(R.id.buttonClose).setOnClickListener(this);
        findViewById(R.id.buttonOk).setOnClickListener(this);
    }

    public void setOnResultListener(OnMyDialogResult onMyDialogResult){
        mDialogResult = onMyDialogResult;
    }

    public interface OnMyDialogResult{
        void finish(boolean isLogout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonClose:
                mDialogResult.finish(false);
                dismiss();
                break;
            case R.id.buttonOk:
                PreferenceUtils.deleteUserInfo(getContext());
                mDialogResult.finish(true);
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
        }
    }


}
