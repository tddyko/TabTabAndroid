package com.yjrlab.tabdoctor.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;

/**
 * Created by yeonjukim on 2017. 6. 5..
 */

public class DialogBase extends AppCompatDialog{

    public DialogBase(Context context) {
        this(context, 0);
    }

    public DialogBase(Context context, int theme) {
        super(context, theme);
        String dialogName = getClass().getSimpleName();
        Log.d("###", "Open Dialog Name:" + dialogName);
    }



}
