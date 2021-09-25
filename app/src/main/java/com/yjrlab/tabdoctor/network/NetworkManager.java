package com.yjrlab.tabdoctor.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public class NetworkManager {
    private static final String BASE_URL = "http://api.tapdoctor.co.kr/";
    public static final int FLAG_PAGE_OFFSET = 50;
    //http://api.tapdoctor.co.kr/
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(new EnumRetrofitConverterFactory())
            .build();

    public static String makeImageUrl(String image) {
        if (image.startsWith("/")) {
            return BASE_URL + image.replaceFirst("/", "");
        } else {
            return BASE_URL  + image;
        }
    }

    public static String makeMainImageUrl(String image) {
        if (image.startsWith("/")) {
            Log.d("##",BASE_URL + "images/maindisplay/" + image.replaceFirst("/", ""));
            return BASE_URL + "images/maindisplay/" + image.replaceFirst("/", "");
        } else {
            Log.d("##", BASE_URL + "images/maindisplay/" + image);
            return BASE_URL + "images/maindisplay/" + image;
        }
    }

    public static String makeBannerImageUrl(String image) {
        if (image.startsWith("/")) {
            Log.d("##",BASE_URL + "images/banner/" + image.replaceFirst("/", ""));
            return BASE_URL + "images/banner/" + image.replaceFirst("/", "");
        } else {
            Log.d("##", BASE_URL + "images/banner/" + image);
            return BASE_URL + "images/banner/" + image;
        }
    }




    public static class EnumRetrofitConverterFactory extends Converter.Factory {
        @Override
        public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            Converter<?, String> converter = null;
            if (type instanceof Class && ((Class<?>) type).isEnum()) {
                converter = new Converter<Object, String>() {
                    @Override
                    public String convert(@NonNull Object value) throws IOException {
                        return GetSerializedNameValue((Enum) value);
                    }
                };

            }
            return converter;
        }
    }


    @Nullable
    public static <E extends Enum<E>> String GetSerializedNameValue(E e) {
        String value = null;
        try {
            System.out.println(e.getClass().getField(e.name()).getAnnotation(SerializedName.class));
            value = e.getClass().getField(e.name()).getAnnotation(SerializedName.class).value();
        } catch (NoSuchFieldException exception) {
            exception.printStackTrace();
        }
        return value;
    }
}
