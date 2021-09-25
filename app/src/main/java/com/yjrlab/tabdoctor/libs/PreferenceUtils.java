package com.yjrlab.tabdoctor.libs;

import android.content.Context;

import com.yjrlab.tabdoctor.network.enums.NPorgStatus;
import com.yjrlab.tabdoctor.view.setting.ShowTypeEnum;

/**
 * Created by jongrakmoon on 2017. 6. 8..
 */

public class PreferenceUtils {
    private static final String TAG = "SETTING";
    private static final String PREFERENCE_KEY_EMAIL = "email";
    private static final String PREFERENCE_KEY_USER_ID = "user_id";
    private static final String PREFERENCE_KEY_GENDER = "gender";
    private static final String PREFERENCE_KEY_NPORG_AGREE = "nporg";



    public static final void saveEmail(Context context, String email) {
        context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .edit()
                .putString(PREFERENCE_KEY_EMAIL, email)
                .apply();
    }

    public static final String loadEmail(Context context) {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getString(PREFERENCE_KEY_EMAIL, null);
    }

    public static final void saveGender(Context context, ShowTypeEnum showTypeEnum) {
        context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .edit()
                .putString(PREFERENCE_KEY_GENDER, showTypeEnum.getType())
                .apply();
    }

    public static final ShowTypeEnum loadGender(Context context) {
        return ShowTypeEnum.parse(context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .getString(PREFERENCE_KEY_GENDER, ""));
    }

    public static final void saveUserId(Context context, long userId) {
        context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .edit()
                .putLong(PREFERENCE_KEY_USER_ID, userId)
                .apply();
    }

    public static final long loadUserId(Context context) {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .getLong(PREFERENCE_KEY_USER_ID, -1);
    }

    /**isRegistered 0: 동의하지않음 1:이미 동의함*/
    public static final void saveNporg(Context context, NPorgStatus nPorgStatus) {
        context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(PREFERENCE_KEY_NPORG_AGREE, nPorgStatus == NPorgStatus.AGREE)
                .apply();
        Dlog.d("saved "+nPorgStatus.toString());
    }

    /** false: 동의하지않음 true:이미 동의함*/
    public static final boolean loadNporg(Context context) {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .getBoolean(PREFERENCE_KEY_NPORG_AGREE, false);
    }

    public static final void deleteUserInfo(Context context) {
        context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        Dlog.d(context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getAll().toString());

    }


}
