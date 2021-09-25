package com.yjrlab.tabdoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public class PopupModel extends BaseModel {

    @SerializedName("pop_id")
    private int id;
    @SerializedName("pop_start_date")
    private String startDate;
    @SerializedName("pop_end_date")
    private String endDate;
    @SerializedName("pop_title")
    private String title;
    @SerializedName("pop_content")
    private String content;
    @SerializedName("pop_disable_hours")
    private int disableHours;
    @SerializedName("pop_activated")
    private boolean activated;
    @SerializedName("pop_datetime")
    private String datetime;

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

    public void setContent(String content) {
        this.content = content;
    }

    public int getDisableHours() {
        return disableHours;
    }

    public void setDisableHours(int disableHours) {
        this.disableHours = disableHours;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
