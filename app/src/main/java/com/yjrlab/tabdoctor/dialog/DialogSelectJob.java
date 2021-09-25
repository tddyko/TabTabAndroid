package com.yjrlab.tabdoctor.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.CheckableButton;


public class DialogSelectJob extends DialogBase implements View.OnClickListener {
    private CheckableButton radioButton1;
    private CheckableButton radioButton2;
    private CheckableButton radioButton3;
    private CheckableButton radioButton4;
    private CheckableButton radioButton5;
    private CheckableButton radioButton6;
    private CheckableButton radioButton7;
    private CheckableButton radioButton8;
    private CheckableButton radioButton9;
    private CheckableButton radioButton10;
    private CheckableButton radioButton11;
    private CheckableButton radioButton12;
    private CheckableButton radioButton13;
    private CheckableButton radioButton14;
    private CheckableButton radioButton15;


    OnMyDialogResultJob mDialogResult;
    private String selected="사무직";

    public DialogSelectJob(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_job);
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
        radioButton8 = (CheckableButton) findViewById(R.id.radioButton8);
        radioButton9 = (CheckableButton) findViewById(R.id.radioButton9);
        radioButton10 = (CheckableButton) findViewById(R.id.radioButton10);
        radioButton11 = (CheckableButton) findViewById(R.id.radioButton11);
        radioButton12 = (CheckableButton) findViewById(R.id.radioButton12);
        radioButton13 = (CheckableButton) findViewById(R.id.radioButton13);
        radioButton14 = (CheckableButton) findViewById(R.id.radioButton14);
        radioButton15 = (CheckableButton) findViewById(R.id.radioButton15);


        (findViewById(R.id.layout1)).setOnClickListener(this);
        (findViewById(R.id.layout2)).setOnClickListener(this);
        (findViewById(R.id.layout3)).setOnClickListener(this);
        (findViewById(R.id.layout4)).setOnClickListener(this);
        (findViewById(R.id.layout5)).setOnClickListener(this);
        (findViewById(R.id.layout6)).setOnClickListener(this);
        (findViewById(R.id.layout7)).setOnClickListener(this);
        (findViewById(R.id.layout8)).setOnClickListener(this);
        (findViewById(R.id.layout9)).setOnClickListener(this);
        (findViewById(R.id.layout10)).setOnClickListener(this);
        (findViewById(R.id.layout11)).setOnClickListener(this);
        (findViewById(R.id.layout12)).setOnClickListener(this);
        (findViewById(R.id.layout13)).setOnClickListener(this);
        (findViewById(R.id.layout14)).setOnClickListener(this);
        (findViewById(R.id.layout15)).setOnClickListener(this);



        radioButton1.setChecked(true);

    }

    public void setDialogResult(DialogSelectJob.OnMyDialogResultJob dialogResult) {
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResultJob {
        void finish(String result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonClose:
                dismiss();
                break;
            case R.id.buttonOk:
                mDialogResult.finish(selected);
                dismiss();
                break;

            case R.id.layout1:
            case R.id.layout2:
            case R.id.layout3:
            case R.id.layout4:
            case R.id.layout5:
            case R.id.layout6:
            case R.id.layout7:
            case R.id.layout8:
            case R.id.layout9:
            case R.id.layout10:
            case R.id.layout11:
            case R.id.layout12:
            case R.id.layout13:
            case R.id.layout14:
            case R.id.layout15:
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
        radioButton8.setChecked(false);
        radioButton9.setChecked(false);
        radioButton10.setChecked(false);
        radioButton11.setChecked(false);
        radioButton12.setChecked(false);
        radioButton13.setChecked(false);
        radioButton14.setChecked(false);
        radioButton15.setChecked(false);


        switch (id) {
            case R.id.layout1:
                radioButton1.setChecked(true);
                selected = "사무직";
                break;
            case R.id.layout2:
                radioButton2.setChecked(true);
                selected = "영업/서비스/마케팅";

                break;
            case R.id.layout3:
                radioButton3.setChecked(true);
                selected = "의료";

                break;
            case R.id.layout4:
                radioButton4.setChecked(true);
                selected = "생산";

                break;
            case R.id.layout5:
                radioButton5.setChecked(true);
                selected = "교육";

                break;

            case R.id.layout6:
                radioButton6.setChecked(true);
                selected = "유통";

                break;
            case R.id.layout7:
                radioButton7.setChecked(true);
                selected = "전문직";

                break;
            case R.id.layout8:
                radioButton8.setChecked(true);
                selected = "건설";

                break;
            case R.id.layout9:
                radioButton9.setChecked(true);
                selected = "디자인";

                break;
            case R.id.layout10:
                radioButton10.setChecked(true);
                selected = "IT";

                break;
            case R.id.layout11:
                radioButton11.setChecked(true);
                selected = "공공";

                break;
            case R.id.layout12:
                radioButton12.setChecked(true);
                selected = "미디어";

                break;
            case R.id.layout13:
                radioButton13.setChecked(true);
                selected = "학생";

                break;
            case R.id.layout14:
                radioButton14.setChecked(true);
                selected = "주부";

                break;
            case R.id.layout15:
                radioButton15.setChecked(true);
                selected = "기타";

                break;
        }
    }

}
