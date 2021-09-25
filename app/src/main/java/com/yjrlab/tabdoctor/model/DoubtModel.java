package com.yjrlab.tabdoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 6..
 */

public class DoubtModel extends BaseModel {
    @SerializedName("db_id")
    private int id;
    @SerializedName("mem_id")
    private int memId;
    @SerializedName("db_body")
    private String spot;
    @SerializedName("db_symptom")
    private String symptom;
    @SerializedName("db_datetime")
    private String regDate;
    @SerializedName("bs_id")
    private int bsId;
    @SerializedName("pob_id")
    private int pobId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemId() {
        return memId;
    }

    public void setMemId(int memId) {
        this.memId = memId;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getBsId() {
        return bsId;
    }

    public void setBsId(int bsId) {
        this.bsId = bsId;
    }

    public int getPobId() {
        return pobId;
    }

    public void setPobId(int pobId) {
        this.pobId = pobId;
    }
}
