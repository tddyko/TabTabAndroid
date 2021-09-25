package com.yjrlab.tabdoctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public class SelfDiagnosisModel extends BaseModel implements Parcelable {
    @SerializedName("msd_id")
    private int id;
    @SerializedName("mem_id")
    private int memId;
    @SerializedName("msd_datetime")
    private String regDate;
    @SerializedName("bs_id")
    private int bsId;
    @SerializedName("bs_rid")
    private int bsRid;
    @SerializedName("pob_id")
    private int pobId;
    @SerializedName("bs_pid")
    private int bsPid;
    @SerializedName("bs_group_code")
    private String bsGroupCode;
    @SerializedName("msd_flag")
    private int msdFlag;
    @SerializedName("bs_level")
    private int bsLevel;
    @SerializedName("pob_name")
    private String pobName;
    @SerializedName("bs_content")
    private String bsContent;
    @SerializedName("dd_explain")
    private String ddExplain;


    @SerializedName("num")
    private int num;

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public String getBsGroupCode() {
        return bsGroupCode;
    }

    public void setBsGroupCode(String bsGroupCode) {
        this.bsGroupCode = bsGroupCode;
    }

    public int getMsdFlag() {
        return msdFlag;
    }

    public void setMsdFlag(int msdFlag) {
        this.msdFlag = msdFlag;
    }

    public int getBsLevel() {
        return bsLevel;
    }

    public void setBsLevel(int bsLevel) {
        this.bsLevel = bsLevel;
    }

    public String getPobName() {
        return pobName;
    }

    public void setPobName(String pobName) {
        this.pobName = pobName;
    }

    public String getBsContent() {
        return bsContent;
    }

    public void setBsContent(String bsContent) {
        this.bsContent = bsContent;
    }

    public String flagName() {
        if (msdFlag == 0) {
            return "평상시 질문";
        } else if (msdFlag == 1) {
            return "자가진단";
        } else {
            return "미등록";
        }
    }

    public int getBsRid() {
        return bsRid;
    }

    public void setBsRid(int bsRid) {
        this.bsRid = bsRid;
    }

    public int getBsPid() {
        return bsPid;
    }

    public void setBsPid(int bsPid) {
        this.bsPid = bsPid;
    }


    public String getDdExplain() {
        return ddExplain;
    }

    public void setDdExplain(String ddExplain) {
        this.ddExplain = ddExplain;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.memId);
        dest.writeString(this.regDate);
        dest.writeInt(this.bsId);
        dest.writeInt(this.bsRid);
        dest.writeInt(this.pobId);
        dest.writeInt(this.bsPid);
        dest.writeString(this.bsGroupCode);
        dest.writeInt(this.msdFlag);
        dest.writeInt(this.bsLevel);
        dest.writeString(this.pobName);
        dest.writeString(this.bsContent);
        dest.writeString(this.ddExplain);
        dest.writeInt(this.num);
    }

    public SelfDiagnosisModel() {
    }

    protected SelfDiagnosisModel(Parcel in) {
        this.id = in.readInt();
        this.memId = in.readInt();
        this.regDate = in.readString();
        this.bsId = in.readInt();
        this.bsRid = in.readInt();
        this.pobId = in.readInt();
        this.bsPid = in.readInt();
        this.bsGroupCode = in.readString();
        this.msdFlag = in.readInt();
        this.bsLevel = in.readInt();
        this.pobName = in.readString();
        this.bsContent = in.readString();
        this.ddExplain = in.readString();
        this.num = in.readInt();
    }

    public static final Parcelable.Creator<SelfDiagnosisModel> CREATOR = new Parcelable.Creator<SelfDiagnosisModel>() {
        @Override
        public SelfDiagnosisModel createFromParcel(Parcel source) {
            return new SelfDiagnosisModel(source);
        }

        @Override
        public SelfDiagnosisModel[] newArray(int size) {
            return new SelfDiagnosisModel[size];
        }
    };
}
