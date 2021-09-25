package com.yjrlab.tabdoctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 6..
 */

public class PushQuestionModel extends BaseModel implements Parcelable {
    @SerializedName("mpq_id")
    private int id;
    @SerializedName("mem_id")
    private int memId;
    @SerializedName("mpq_body")
    private String spot;
    @SerializedName("mpq_answer")
    private String answer;
    @SerializedName("bs_pid")
    private int bsPid;
    @SerializedName("bs_group_code")
    private String bsGroupCode;
    @SerializedName("mpq_flag")
    private int mpqFlag;
    @SerializedName("pob_id")
    private int pobId;
    @SerializedName("mpq_token")
    private String mpqToken;
    @SerializedName("mpq_datetime")
    private String regDate;
    @SerializedName("num")
    private int num;
    @SerializedName("mpr_id")
    private int mprId;
    @SerializedName("bs_id")
    private int bsId;
    @SerializedName("bs_content")
    private String bsContent;
    @SerializedName("bs_level")
    private int bsLevel;
    @SerializedName("dd_explain")
    private String ddExplain;


    public String flagName() {
        if (mpqFlag == 0) {
            return "평상시 질문";
        } else if (mpqFlag == 1) {
            return "자가진단";
        } else {
            return "미등록";
        }
    }

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public int getBsPid() {
        return bsPid;
    }

    public void setBsPid(int bsPid) {
        this.bsPid = bsPid;
    }

    public String getBsGroupCode() {
        return bsGroupCode;
    }

    public void setBsGroupCode(String bsGroupCode) {
        this.bsGroupCode = bsGroupCode;
    }

    public int getMpqFlag() {
        return mpqFlag;
    }

    public void setMpqFlag(int mpqFlag) {
        this.mpqFlag = mpqFlag;
    }

    public int getPobId() {
        return pobId;
    }

    public void setPobId(int pobId) {
        this.pobId = pobId;
    }

    public String getMpqToken() {
        return mpqToken;
    }

    public void setMpqToken(String mpqToken) {
        this.mpqToken = mpqToken;
    }

    public int getMprId() {
        return mprId;
    }

    public void setMprId(int mprId) {
        this.mprId = mprId;
    }

    public int getBsId() {
        return bsId;
    }

    public void setBsId(int bsId) {
        this.bsId = bsId;
    }


    public PushQuestionModel() {
    }

    public String getBsContent() {
        return bsContent;
    }

    public void setBsContent(String bsContent) {
        this.bsContent = bsContent;
    }

    public int getBsLevel() {
        return bsLevel;
    }

    public void setBsLevel(int bsLevel) {
        this.bsLevel = bsLevel;
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
        dest.writeString(this.spot);
        dest.writeString(this.answer);
        dest.writeInt(this.bsPid);
        dest.writeString(this.bsGroupCode);
        dest.writeInt(this.mpqFlag);
        dest.writeInt(this.pobId);
        dest.writeString(this.mpqToken);
        dest.writeString(this.regDate);
        dest.writeInt(this.num);
        dest.writeInt(this.mprId);
        dest.writeInt(this.bsId);
        dest.writeString(this.bsContent);
        dest.writeInt(this.bsLevel);
        dest.writeString(this.ddExplain);
    }

    protected PushQuestionModel(Parcel in) {
        this.id = in.readInt();
        this.memId = in.readInt();
        this.spot = in.readString();
        this.answer = in.readString();
        this.bsPid = in.readInt();
        this.bsGroupCode = in.readString();
        this.mpqFlag = in.readInt();
        this.pobId = in.readInt();
        this.mpqToken = in.readString();
        this.regDate = in.readString();
        this.num = in.readInt();
        this.mprId = in.readInt();
        this.bsId = in.readInt();
        this.bsContent = in.readString();
        this.bsLevel = in.readInt();
        this.ddExplain = in.readString();
    }

    public static final Parcelable.Creator<PushQuestionModel> CREATOR = new Parcelable.Creator<PushQuestionModel>() {
        @Override
        public PushQuestionModel createFromParcel(Parcel source) {
            return new PushQuestionModel(source);
        }

        @Override
        public PushQuestionModel[] newArray(int size) {
            return new PushQuestionModel[size];
        }
    };
}
