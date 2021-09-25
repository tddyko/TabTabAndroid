package com.yjrlab.tabdoctor.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public class ResponseModel<C> extends BaseModel {

    @SerializedName("status")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("total_rows")
    private int totalContentsCount;

    @SerializedName("view")
    private C content;

    @SerializedName("M")
    private C man;
    @SerializedName("F")
    private C woman;
    @SerializedName("C")
    private C children;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public C getContent() {
        return content;
    }

    public void setContent(C content) {
        this.content = content;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return TextUtils.isEmpty(success) || success.equals("true") || success.equals("1");
    }

    public C getMan() {
        return man;
    }

    public void setMan(C man) {
        this.man = man;
    }

    public C getWoman() {
        return woman;
    }

    public void setWoman(C woman) {
        this.woman = woman;
    }

    public C getChildren() {
        return children;
    }

    public void setChildren(C children) {
        this.children = children;
    }

    public int getTotalContentsCount() {
        return totalContentsCount;
    }

    public void setTotalContentsCount(int totalContentsCount) {
        this.totalContentsCount = totalContentsCount;
    }
}
