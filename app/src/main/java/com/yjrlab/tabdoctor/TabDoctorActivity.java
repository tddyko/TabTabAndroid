package com.yjrlab.tabdoctor;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by yeonjukim on 2017. 6. 5..
 */

public class TabDoctorActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected Context getContext() {
        return this;
    }
}
