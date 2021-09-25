package com.yjrlab.tabdoctor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.Dlog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class DialogNumberPicker extends DialogBase implements View.OnClickListener {
    private Set<Calendar> result = new HashSet<>();
    public OnFinishResultListener onFinishResultListener;
    private NumberPicker npY1;
    private NumberPicker npM1;
    private NumberPicker npD1;
    private NumberPicker npY2;
    private NumberPicker npM2;
    private NumberPicker npD2;


    public DialogNumberPicker(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_numberpicker);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setLayout();
        showBirthDayPicker();
    }

    private void setLayout() {
        findViewById(R.id.buttonOk).setOnClickListener(this);
        findViewById(R.id.buttonCancel).setOnClickListener(this);
        findViewById(R.id.buttonClose).setOnClickListener(this);

    }

    public interface OnFinishResultListener {
        void onFinish(int date1, int date2);
    }

    public void setOnFinishResultListner(OnFinishResultListener onFinishResultListner) {
        this.onFinishResultListener = onFinishResultListner;
    }

    private void showBirthDayPicker() {
        Calendar calendar = Calendar.getInstance();
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH) + 1;
        int date2 = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, -3);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);


        npY1 = (NumberPicker) findViewById(R.id.numberPickerYear1);
        npY1.setMinValue(year);
        npY1.setMaxValue(year + 100);
        npY1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        npY1.setWrapSelectorWheel(false);
        npY1.setValue(year);

        npY2 = (NumberPicker) findViewById(R.id.numberPickerYear2);
        npY2.setMinValue(year2);
        npY2.setMaxValue(year2 + 100);
        npY2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        npY2.setWrapSelectorWheel(false);
        npY2.setValue(year2);

        npM1 = (NumberPicker) findViewById(R.id.numberPickerMonth1);
        npM1.setMinValue(1);
        npM1.setMaxValue(12);
        npM1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        npM1.setWrapSelectorWheel(false);
        npM1.setValue(month);
        npM1.computeScroll();


        npM2 = (NumberPicker) findViewById(R.id.numberPickerMonth2);
        npM2.setMinValue(1);
        npM2.setMaxValue(12);
        npM2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        npM2.setWrapSelectorWheel(false);
        npM2.setValue(month2);

        npD1 = (NumberPicker) findViewById(R.id.numberPickerDate1);
        npD1.setMinValue(1);
        npD1.setMaxValue(31);
        npD1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        npD1.setWrapSelectorWheel(false);
        npD1.setValue(date);

        npD2 = (NumberPicker) findViewById(R.id.numberPickerDate2);
        npD2.setMinValue(1);
        npD2.setMaxValue(31);
        npD2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        npD2.setWrapSelectorWheel(false);
        npD2.setValue(date2);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonOk:
                String m1 = npM1.getValue() + "";
                String m2 = npM2.getValue() + "";
                String d1 = npD1.getValue() + "";
                String d2 = npD2.getValue() + "";

                if (npM1.getValue() < 10) {
                    m1 = "0" + npM1.getValue();
                }
                if (npM2.getValue() < 10) {
                    m2 = "0" + npM2.getValue();
                }
                if (npD1.getValue() < 10) {
                    d1 = "0" + npD1.getValue();
                }
                if (npD2.getValue() < 10) {
                    d2 = "0" + npD2.getValue();
                }

                String c1 = npY1.getValue() + m1 + d1;
                String c2 = npY2.getValue() + m2 + d2;

                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                String today = format.format(date);
                int date1 = Integer.parseInt(c1);
                int date2 = Integer.parseInt(c2);

                if (Integer.parseInt(c1) > Integer.parseInt(c2)) {
                    Toast.makeText(getContext(), "시작날짜가 종료날짜보다 날짜가 커야합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(today) < Integer.parseInt(c2)) {
                    Toast.makeText(getContext(), "종료날짜가 오늘보다 날짜가 작아야합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }


                onFinishResultListener.onFinish(date1,date2);
                dismiss();
                break;
            case R.id.buttonCancel:
            case R.id.buttonClose:
                dismiss();
                break;


        }
    }
}
