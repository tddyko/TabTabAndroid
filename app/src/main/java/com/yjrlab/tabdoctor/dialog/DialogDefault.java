package com.yjrlab.tabdoctor.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.yjrlab.tabdoctor.R;


public class DialogDefault extends DialogBase implements View.OnClickListener {
    private final String text;

    public DialogDefault(@NonNull Context context, @NonNull String text) {
        super(context);
        this.text = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_default);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ((TextView)findViewById(R.id.textView)).setText(text);
        findViewById(R.id.buttonClose).setOnClickListener(this);
        findViewById(R.id.buttonOk).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonClose:
            case R.id.buttonOk:
                dismiss();
                break;
        }
    }
}
