package com.yjrlab.tabdoctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jongrakmoon on 2017. 6. 8..
 */

public class BodyPartSymptom extends BaseModel implements Parcelable {
    @SerializedName("pob_id")
    private int pobId;
    @SerializedName("pob_name")
    private String pobName;
    @SerializedName("pob_group")
    private String pobGroup;
    @SerializedName("bs_id")
    private int bsId;
    @SerializedName("bs_pid")
    private int bsPid;
    @SerializedName("bs_level")
    private int bsLevel;
    @SerializedName("bs_group_code")
    private String bsGroupCode;
    @SerializedName("bs_content")
    private String bsContent;
    @SerializedName("dd_code")
    private String ddCode;
    @SerializedName("dd_category")
    private String ddCategory;
    @SerializedName("dd_name")
    private String ddName;
    @SerializedName("dd_explain")
    private String ddExplain;
    @SerializedName("num")
    private int num;

    public static Map<String, List<BodyPartSymptom>> groupByBodyPartWithPobName(List<BodyPartSymptom> bodyPartSymptoms) {
        HashMap<String, List<BodyPartSymptom>> result = new HashMap<>();

        for (BodyPartSymptom bodyPartSymptom : bodyPartSymptoms) {
            List<BodyPartSymptom> data = result.get(bodyPartSymptom.getPobName());
            if (data == null) {
                data = new ArrayList<>();
            }
            data.add(bodyPartSymptom);
            result.put(bodyPartSymptom.getPobName(), data);
        }

        return result;
    }

    public static String toPostDoubtQuery(List<BodyPartSymptom> symptoms) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (BodyPartSymptom symptom : symptoms) {
            builder.append("{\"pob_id\":")
                    .append(symptom.pobId)
                    .append(",\"bs_id\":")
                    .append(symptom.bsId)
                    .append("},");
        }
        builder.deleteCharAt(builder.length() - 1)
                .append(']');
        return builder.toString();
    }

    public int getPobId() {
        return pobId;
    }

    public void setPobId(int pobId) {
        this.pobId = pobId;
    }

    public String getPobName() {
        return pobName;
    }

    public void setPobName(String pobName) {
        this.pobName = pobName;
    }

    public String getPobGroup() {
        return pobGroup;
    }

    public void setPobGroup(String pobGroup) {
        this.pobGroup = pobGroup;
    }

    public int getBsId() {
        return bsId;
    }

    public void setBsId(int bsId) {
        this.bsId = bsId;
    }

    public int getBsPid() {
        return bsPid;
    }

    public void setBsPid(int bsPid) {
        this.bsPid = bsPid;
    }

    public int getBsLevel() {
        return bsLevel;
    }

    public void setBsLevel(int bsLevel) {
        this.bsLevel = bsLevel;
    }

    public String getBsGroupCode() {
        return bsGroupCode;
    }

    public void setBsGroupCode(String bsGroupCode) {
        this.bsGroupCode = bsGroupCode;
    }

    public String getBsContent() {
        return bsContent;
    }

    public void setBsContent(String bsContent) {
        this.bsContent = bsContent;
    }

    public String getDdCode() {
        return ddCode;
    }

    public void setDdCode(String ddCode) {
        this.ddCode = ddCode;
    }

    public String getDdCategory() {
        return ddCategory;
    }

    public void setDdCategory(String ddCategory) {
        this.ddCategory = ddCategory;
    }

    public String getDdName() {
        return ddName;
    }

    public void setDdName(String ddName) {
        this.ddName = ddName;
    }

    public String getDdExplain() {
        return ddExplain;
    }

    public void setDdExplain(String ddExplain) {
        this.ddExplain = ddExplain;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pobId);
        dest.writeString(this.pobName);
        dest.writeString(this.pobGroup);
        dest.writeInt(this.bsId);
        dest.writeInt(this.bsPid);
        dest.writeInt(this.bsLevel);
        dest.writeString(this.bsGroupCode);
        dest.writeString(this.bsContent);
        dest.writeString(this.ddCode);
        dest.writeString(this.ddCategory);
        dest.writeString(this.ddName);
        dest.writeString(this.ddExplain);
        dest.writeInt(this.num);
    }

    public BodyPartSymptom() {
    }

    protected BodyPartSymptom(Parcel in) {
        this.pobId = in.readInt();
        this.pobName = in.readString();
        this.pobGroup = in.readString();
        this.bsId = in.readInt();
        this.bsPid = in.readInt();
        this.bsLevel = in.readInt();
        this.bsGroupCode = in.readString();
        this.bsContent = in.readString();
        this.ddCode = in.readString();
        this.ddCategory = in.readString();
        this.ddName = in.readString();
        this.ddExplain = in.readString();
        this.num = in.readInt();
    }

    public static final Parcelable.Creator<BodyPartSymptom> CREATOR = new Parcelable.Creator<BodyPartSymptom>() {
        @Override
        public BodyPartSymptom createFromParcel(Parcel source) {
            return new BodyPartSymptom(source);
        }

        @Override
        public BodyPartSymptom[] newArray(int size) {
            return new BodyPartSymptom[size];
        }
    };

    public static String toPostSelfDiagnosisQuery(List<BodyPartSymptom> symptoms) {
        StringBuilder builder = new StringBuilder();
        BodyPartSymptom symptom = symptoms.get(symptoms.size() - 1);
        builder.append("[{\"pob_id\":")
                .append(symptom.getPobId())
                .append(",\"bs_id\":")
                .append(symptom.getBsId())
                .append("}]");
        return builder.toString();
    }


    public String toPostPushQuery(int mprId) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"mpr_id\":")
                .append(mprId)
                .append(",\"bs_id\":")
                .append(this.bsId)
                .append(",\"pob_id\":")
                .append(this.pobId)
                .append("}");
        return builder.toString();
    }

}
