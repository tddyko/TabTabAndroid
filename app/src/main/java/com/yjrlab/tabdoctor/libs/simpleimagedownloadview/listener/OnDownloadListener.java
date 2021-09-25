package com.yjrlab.tabdoctor.libs.simpleimagedownloadview.listener;

import java.net.URL;

public interface OnDownloadListener {
    void onStartImageDownload(URL downloadURL);

    void onFinishedImageDownload(boolean isSuccess, boolean isCached);

    void onFailedDownloadImage(Exception e);
}
