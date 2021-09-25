package com.yjrlab.tabdoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public class MainContentModel extends BaseModel {

    @SerializedName("md_id")
    private int id;
    @SerializedName("md_start_date")
    private String startDate;
    @SerializedName("md_end_date")
    private String endDate;
    @SerializedName("md_title")
    private String title;
    @SerializedName("md_content")
    private String content;
    @SerializedName("md_url")
    private String url;
    @SerializedName("md_hit")
    private int hit;
    @SerializedName("md_image")
    private String image;
    @SerializedName("md_activated")
    private boolean activated;
    @SerializedName("md_datetime")
    private String regDate;
    @SerializedName("num")
    private int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getShortContent() {
        if (content != null) {
            if (content.length() > 40) {
                return content.substring(0, 40) + "...";
            } else {
                return content;
            }
        } else {
            return "";
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
