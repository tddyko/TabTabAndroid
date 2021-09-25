package com.yjrlab.tabdoctor.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.yjrlab.tabdoctor.R;


public class DialogTermsOfUse extends DialogBase implements View.OnClickListener {
    @NonNull
    private final Context context;
    @NonNull
    private final String title;
    @NonNull
    private final String desc;

    public DialogTermsOfUse(@NonNull Context context, @NonNull String title, @NonNull String desc) {
        super(context);
        this.context = context;
        this.title = title;
        this.desc = desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_terms_of_use);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        findViewById(R.id.buttonClose).setOnClickListener(this);
        findViewById(R.id.buttonOk).setOnClickListener(this);

        ((TextView)findViewById(R.id.textViewTitle)).setText(title);
        ((TextView)findViewById(R.id.textViewDesc)).setText(desc);

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
