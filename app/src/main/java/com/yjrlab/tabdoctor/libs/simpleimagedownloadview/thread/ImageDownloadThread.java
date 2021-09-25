package com.yjrlab.tabdoctor.libs.simpleimagedownloadview.thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;


import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.libs.simpleimagedownloadview.listener.OnDownloadListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MoonJR on 2015. 11. 8..
 */

public class ImageDownloadThread extends Thread implements OnDownloadListener {

    private final String TAG = ImageDownloadThread.class.getSimpleName();

    private static boolean isDebugMode = false;

    private OnDownloadListener mOnDownloadListenerDebug;
    private OnDownloadListener mOnDownloadListenerUser;

    private URL url;
    private ImageView mImageView;
    private Context mContext;

    private boolean isCache;

    private int sampleSize;

    private long cacheTimeout;

    private int notFoundImage;

    public ImageDownloadThread(Context context, ImageView mImageView, URL url) {
        this.mContext = context;
        this.url = url;
        this.mImageView = mImageView;
        Thread preThread = (Thread) mImageView.getTag();
        if (preThread != null) {
            preThread.interrupt();
        }
        this.mImageView.setTag(this);
        this.sampleSize = 1;
        this.mOnDownloadListenerDebug = this;
        this.isCache = true;
        this.cacheTimeout = Long.MAX_VALUE;
        notFoundImage = 0;
    }

    public void setNotFoundImage(int resource) {
        this.notFoundImage = resource;
    }

    public ImageDownloadThread(Context context, ImageView mImageView, String url) throws MalformedURLException {
        this(context, mImageView, new URL(url));
    }


    public ImageDownloadThread(Context context, ImageView mImageView, URL url, int sampleSize) {
        this(context, mImageView, url);
        this.sampleSize = sampleSize;
    }

    public ImageDownloadThread(Context context, ImageView mImageView, String url, int sampleSize) throws MalformedURLException {
        this(context, mImageView, new URL(url), sampleSize);
    }


    public void setOnDownloadListener(@Nullable OnDownloadListener onDownloadListener) {
        this.mOnDownloadListenerUser = onDownloadListener;
    }

    public void setSampleSize(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    public int getSampleSize() {
        return this.sampleSize;
    }

    public void setCache(boolean isCache) {
        this.isCache = isCache;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setURL(URL url) {
        this.url = url;
    }

    public URL getURL() {
        return url;
    }

    private void throwException() {
        if (mContext == null) {
            throw new IllegalStateException("must not context null!!!");
        } else if (url == null) {
            throw new IllegalStateException("must not url null!!!");
        } else if (mImageView == null) {
            throw new IllegalStateException("must not imageView null!!!");
        }
    }

    private boolean isCachedImage = false;

    @Override
    public void run() {
        throwException();
        try {
            mOnDownloadListenerDebug.onStartImageDownload(url);
            final Bitmap image;
            if (isCache) {
                File cacheImageFile = getCacheFile(mContext, url);
                if (cacheImageFile != null) {
                    image = isCacheTimeout(cacheImageFile) ? getImageURL(url) : getImageCache(cacheImageFile);
                } else {
                    image = getImageURL(url);
                }
            } else {
                image = getImageURL(url);
            }
            setImageView(image, mImageView);
            mOnDownloadListenerDebug.onFinishedImageDownload(true, isCachedImage);
        } catch (Exception e) {
            if (notFoundImage != 0) {
                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageResource(notFoundImage);
                    }
                });
            }
            mOnDownloadListenerDebug.onFailedDownloadImage(e);
            mOnDownloadListenerDebug.onFinishedImageDownload(false, isCachedImage);
        }

    }

    private boolean isCacheTimeout(File cacheImageFile) {
        long nowTime = System.currentTimeMillis();
        long fileModTime = cacheImageFile.lastModified();

        return nowTime - fileModTime > getCacheTimeout();
    }

    private File getCacheFile(Context mContext, URL url) {
        //if cache file not exist return null;

        File cacheImageFile = new File(mContext.getExternalCacheDir(), url.hashCode() + "_" + sampleSize);
        isCachedImage = true;
        return cacheImageFile.exists() ? cacheImageFile : null;

    }

    private Bitmap getImageURL(URL url) throws IOException {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = sampleSize;

            InputStream imageStream = url.openConnection().getInputStream();
            Bitmap image = BitmapFactory.decodeStream(imageStream, null, options);
            imageStream.close();
            if (isCache) {
                FileOutputStream outputStream = new FileOutputStream(new File(mContext.getExternalCacheDir(), url.hashCode() + "_" + sampleSize));
                image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            }
            isCachedImage = false;
            return image;
        } catch (OutOfMemoryError e) {
            return null;
        }


    }

    private Bitmap getImageCache(File cacheImageFile) throws IOException {
        InputStream imageStream = new FileInputStream(cacheImageFile);
        Bitmap image = BitmapFactory.decodeStream(imageStream);
        imageStream.close();
        return image;
    }

    private void setImageView(final Bitmap image, final ImageView mImageView) {

        mImageView.post(new Runnable() {
            @Override
            public void run() {
                if (image != null) {
                    mImageView.setImageBitmap(image);
                    mImageView.setBackground(null);
                    new WeakReference<>(image);
                } else {
                    mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    mImageView.setImageResource(R.drawable.icon_logo);
                }

            }
        });

    }

    public static void setDebugMode(boolean debugMode) {
        isDebugMode = debugMode;
    }

    public static boolean isDebugMode() {
        return isDebugMode;
    }

    public void setCacheTimeout(long cacheTimeout) {
        this.cacheTimeout = cacheTimeout;
    }

    public long getCacheTimeout() {
        return this.cacheTimeout;
    }


    @Override
    public void onStartImageDownload(final URL downloadURL) {
        if (isDebugMode) {
            Log.d(TAG, "Start Download Image from " + downloadURL);
        }

        if (mOnDownloadListenerUser != null) {
            mImageView.post(new Runnable() {
                @Override
                public void run() {
                    mOnDownloadListenerUser.onStartImageDownload(downloadURL);
                }
            });
        }
    }

    @Override
    public void onFinishedImageDownload(final boolean isSuccess, final boolean isCached) {

        if (isDebugMode) {
            if (isSuccess) {
                Log.d(TAG, "Success download!!!");
                if (isCached) {
                    Log.d(TAG, "image is cached image");
                }
            } else {
                Log.d(TAG, "Fail download!!!");
            }
        }

        if (mOnDownloadListenerUser != null) {
            mImageView.post(new Runnable() {
                @Override
                public void run() {
                    mOnDownloadListenerUser.onFinishedImageDownload(isSuccess, isCached);
                }
            });
        }

    }

    @Override
    public void onFailedDownloadImage(final Exception e) {
        if (isDebugMode) {
            Log.d(TAG, "Download Error!!!" + e.getMessage(), e);
        }
        if (mOnDownloadListenerUser != null) {
            mImageView.post(new Runnable() {
                @Override
                public void run() {
                    mOnDownloadListenerUser.onFailedDownloadImage(e);
                }
            });
        }
    }


}