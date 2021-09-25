package com.yjrlab.tabdoctor.network.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jongrak on 2017. 8. 16..
 */

public class BaseSessionInterceptor {

    private static final String KEY_SESSION_STORE = "SESSION";
    private static final String KEY_COOKIES = "COOKIES";

    private SharedPreferences sessionPreferences;

    protected BaseSessionInterceptor(Context context) {
        this.sessionPreferences = context.getSharedPreferences(KEY_SESSION_STORE, Context.MODE_PRIVATE);
    }

    protected Set<String> getCookies() {
        return sessionPreferences.getStringSet(KEY_COOKIES, new HashSet<String>());
    }

    protected void setCookies(Set<String> cookies) {
        sessionPreferences
                .edit()
                .putStringSet(KEY_COOKIES, cookies)
                .apply();
    }

    public static void clearCookies(Context context) {
        context.getSharedPreferences(KEY_SESSION_STORE, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }
}
