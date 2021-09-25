package com.yjrlab.tabdoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 6..
 */

public class HealthDicModel extends BaseModel {

    @SerializedName("hd_code")
    private int code;
    @SerializedName("hd_name")
    private String name;
    @SerializedName("hd_symptom")
    private String symptom;
    @SerializedName("hd_medical_title")
    private String medicalTitle;
    @SerializedName("hd_content")
    private String content;
    @SerializedName("hd_datetime")
    private String regDate;
    @SerializedName("mo_code")
    private int departmentCode;
    @SerializedName("mo_name")
    private String departmentName;
    @SerializedName("num")
    private int num;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getMedicalTitle() {
        return medicalTitle;
    }

    public void setMedicalTitle(String medicalTitle) {
        this.medicalTitle = medicalTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(int departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
