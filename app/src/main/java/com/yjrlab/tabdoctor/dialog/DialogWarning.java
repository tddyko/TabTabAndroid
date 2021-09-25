package com.yjrlab.tabdoctor.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.CheckableButton;

import java.text.SimpleDateFormat;

import static com.yjrlab.tabdoctor.view.main.MainActivity.PRE_WARNING;


public class DialogWarning extends DialogBase implements View.OnClickListener {
    private CheckableButton checkableButton;

    public DialogWarning(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_warning);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        findViewById(R.id.buttonClose).setOnClickListener(this);
        findViewById(R.id.buttonOk).setOnClickListener(this);
        findViewById(R.id.layoutContents).setOnClickListener(this);

        checkableButton = (CheckableButton) findViewById(R.id.checkableButton);
        checkableButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonClose:
            case R.id.buttonOk:
                dismiss();
                getContext().getSharedPreferences(PRE_WARNING, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis()), !checkableButton.isChecked())  //PRE_WARNING이 true면 dialog를 show하므로 반대로 값 저장
                        .apply();
                break;
            case R.id.layoutContents:
                checkableButton.setChecked(true);
                break;
        }
    }
}
