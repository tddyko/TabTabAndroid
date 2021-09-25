package com.yjrlab.tabdoctor.libs.simpleimagedownloadview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.yjrlab.tabdoctor.libs.simpleimagedownloadview.listener.OnDownloadListener;
import com.yjrlab.tabdoctor.libs.simpleimagedownloadview.thread.ImageDownloadThread;

import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MoonJR on 2015. 11. 7..
 */
public class SimpleCircleImageDownloadView extends CircleImageView {

    private Context mContext;
    private boolean isCache = true;
    private OnDownloadListener mOnDownloadListener;
    private String url;


    public SimpleCircleImageDownloadView(Context context) {
        super(context);
        this.mContext = context;
    }

    public SimpleCircleImageDownloadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public SimpleCircleImageDownloadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void setCache(boolean isCache) {
        this.isCache = isCache;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setOnDownloadListener(OnDownloadListener listener) {
        mOnDownloadListener = listener;
    }


    public void setImageURL(String url) {
        setImageURL(url, 1);
    }

    public void setImageURL(String url, int sampleSize) {
        this.url = url;
        try {
            setImageURL(new URL(url), sampleSize);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void setImageURL(@Nullable URL url, int sampleSize) {
        ImageDownloadThread thread = new ImageDownloadThread(mContext, this, url);
        thread.setSampleSize(sampleSize);
        thread.setCache(isCache);
        thread.setOnDownloadListener(mOnDownloadListener);
        thread.start();
    }


    public String getUrl() {
        return url;
    }

}
