package com.yjrlab.tabdoctor;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by yeonjukim on 2017. 6. 5..
 */

public class TabDoctorApplication extends Application {

    public static boolean DEBUG = false;

    @Override
    public void onCreate() {
        super.onCreate();
        this.DEBUG = isDebuggable(this);
        setCacheImageView();
        setFont();

//                .addNormal(Typekit.createFromAsset(this, "fonts/nanumbarungothiclight.ttf"))
//                .addBold(Typekit.createFromAsset(this, "fonts/nanumbarungothic.ttf"))
//                .add("ul",Typekit.createFromAsset(this, "fonts/nanumbarungothicultralight.ttf"))
//                .add("gb",Typekit.createFromAsset(this, "fonts/nanumbarungothicbold.ttf"))
//                .add("ar",Typekit.createFromAsset(this,"arlrdbd.ttf"));

    }

    private void setFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/nanumbarungothiclight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    private void setCacheImageView() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(options)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 현재 디버그모드여부를 리턴
     *
     * @param context
     * @return
     */
    private boolean isDebuggable(Context context) {
        boolean debuggable = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
        /* debuggable variable will remain false */
        }

        return debuggable;
    }


}

