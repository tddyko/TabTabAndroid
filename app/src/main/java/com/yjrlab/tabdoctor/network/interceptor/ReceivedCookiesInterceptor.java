package com.yjrlab.tabdoctor.network.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by jongrak on 2017. 8. 16..
 */

public class ReceivedCookiesInterceptor extends BaseSessionInterceptor implements Interceptor {


    public ReceivedCookiesInterceptor(Context context) {
        super(context);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            setCookies(cookies);
        }
        return originalResponse;
    }
}
