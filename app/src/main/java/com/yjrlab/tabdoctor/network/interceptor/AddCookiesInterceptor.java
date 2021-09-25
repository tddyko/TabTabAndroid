package com.yjrlab.tabdoctor.network.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jongrak on 2017. 8. 16..
 */

public class AddCookiesInterceptor extends BaseSessionInterceptor implements Interceptor {
    public AddCookiesInterceptor(Context context) {
        super(context);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        Set<String> preferences = getCookies();
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }
        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android");
        return chain.proceed(builder.build());
    }
}
