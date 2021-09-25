package com.yjrlab.tabdoctor.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.CheckableButton;


public class DialogSelectAge extends DialogBase implements View.OnClickListener {
    private CheckableButton radioButton1;
    private CheckableButton radioButton2;
    private CheckableButton radioButton3;
    private CheckableButton radioButton4;
    private CheckableButton radioButton5;
    private CheckableButton radioButton6;
    private CheckableButton radioButton7;

    OnMyDialogResult mDialogResult;
    private String selected = "소아";

    public DialogSelectAge(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_age);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        findViewById(R.id.buttonClose).setOnClickListener(this);
        findViewById(R.id.buttonOk).setOnClickListener(this);
        radioButton1 = (CheckableButton) findViewById(R.id.radioButton1);
        radioButton2 = (CheckableButton) findViewById(R.id.radioButton2);
        radioButton3 = (CheckableButton) findViewById(R.id.radioButton3);
        radioButton4 = (CheckableButton) findViewById(R.id.radioButton4);
        radioButton5 = (CheckableButton) findViewById(R.id.radioButton5);
        radioButton6 = (CheckableButton) findViewById(R.id.radioButton6);
        radioButton7 = (CheckableButton) findViewById(R.id.radioButton7);

        (findViewById(R.id.layout1)).setOnClickListener(this);
        (findViewById(R.id.layout2)).setOnClickListener(this);
        (findViewById(R.id.layout3)).setOnClickListener(this);
        (findViewById(R.id.layout4)).setOnClickListener(this);
        (findViewById(R.id.layout5)).setOnClickListener(this);
        (findViewById(R.id.layout6)).setOnClickListener(this);
        (findViewById(R.id.layout7)).setOnClickListener(this);


        radioButton1.setChecked(true);

    }

    public void setDialogResult(OnMyDialogResult dialogResult) {
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult {
        void finish(String result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonClose:
                dismiss();
                break;
            case R.id.buttonOk:
                if (mDialogResult != null) {
                    mDialogResult.finish(selected);
                }
                dismiss();
                break;

            case R.id.layout1:
            case R.id.layout2:
            case R.id.layout3:
            case R.id.layout4:
            case R.id.layout5:
            case R.id.layout6:
            case R.id.layout7:
                setRadioButton(v.getId());
                break;
        }
    }

    private void setRadioButton(int id) {
        radioButton1.setChecked(false);
        radioButton2.setChecked(false);
        radioButton3.setChecked(false);
        radioButton4.setChecked(false);
        radioButton5.setChecked(false);
        radioButton6.setChecked(false);
        radioButton7.setChecked(false);

        switch (id) {
            case R.id.layout1:
                radioButton1.setChecked(true);
                selected = "소아";
                break;

            case R.id.layout2:
                radioButton2.setChecked(true);
                selected = "10대";
                break;
            case R.id.layout3:
                radioButton3.setChecked(true);
                selected = "20대";
                break;

            case R.id.layout4:
                radioButton4.setChecked(true);
                selected = "30대";
                break;

            case R.id.layout5:
                radioButton5.setChecked(true);
                selected = "40대";
                break;

            case R.id.layout6:
                radioButton6.setChecked(true);
                selected = "50대";
                break;

            case R.id.layout7:
                radioButton7.setChecked(true);
                selected = "60대 이상";
                break;
        }
    }
}
