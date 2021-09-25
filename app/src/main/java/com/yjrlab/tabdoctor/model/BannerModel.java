package com.yjrlab.tabdoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public class BannerModel extends BaseModel {
    @SerializedName("ban_id")
    private int id;
    @SerializedName("ban_start_date")
    private String startDate;
    @SerializedName("ban_end_date")
    private String endDate;
    @SerializedName("ban_title")
    private String title;
    @SerializedName("ban_url")
    private String url;
    @SerializedName("ban_hit")
    private int hit;
    @SerializedName("ban_image")
    private String image;
    @SerializedName("ban_activated")
    private boolean activated;
    @SerializedName("ban_datetime")
    private String regDate;

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
}
