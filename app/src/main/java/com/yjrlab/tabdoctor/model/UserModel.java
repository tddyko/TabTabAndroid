package com.yjrlab.tabdoctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.network.enums.NPorgStatus;
import com.yjrlab.tabdoctor.view.setting.ShowTypeEnum;

import static com.yjrlab.tabdoctor.view.setting.ShowTypeEnum.BABY;
import static com.yjrlab.tabdoctor.view.setting.ShowTypeEnum.MAN;
import static com.yjrlab.tabdoctor.view.setting.ShowTypeEnum.WOMAN;

/**
 * Created by jongrakmoon on 2017. 6. 2..
 */

public class UserModel extends BaseModel implements Parcelable {
    public static final String REFER_NAVER = "naver";
    public static final String REFER_GOOGLE = "google";
    public static final String REFER_FACEBOOK = "facebook";

    public static final int GENDER_MAN = 1;
    public static final int GENDER_WOMAN = 2;

    public static final int NPROG_AGREE = 1;
    public static final int NPROG_DENY = 0;


    @SerializedName("mem_email")
    private String email;
    @SerializedName("mem_password")
    private String password;
    @SerializedName("mem_username")
    private String name;
    @SerializedName("mem_sex")
    private int sex;
    @SerializedName("mem_age")
    private String age;
    @SerializedName("mem_id")
    private long id;
    @SerializedName("mem_job")
    private String job;
    @SerializedName("mem_sns_refer")
    private String snsRefer;
    @SerializedName("mem_nporg_agree")
    private int nprogAgree;
    @SerializedName("mem_register_datetime")
    private String regDate;
    @SerializedName("mem_nporg_datetime")
    private String nprogDate;
    @SerializedName("mem_token")
    private String token;
    @SerializedName("mem_os")
    private String memOs;



    public int getNprogAgree() {
        return nprogAgree;
    }

    public NPorgStatus getNporgStatusEnum() {
        return nprogAgree == 0 ? NPorgStatus.DENY : NPorgStatus.AGREE;
    }

    public void setNprogAgree(int nprogAgree) {
        this.nprogAgree = nprogAgree;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getNprogDate() {
        return nprogDate;
    }

    public void setNprogDate(String nprogDate) {
        this.nprogDate = nprogDate;
    }

    public static int getNprogDeny() {
        return NPROG_DENY;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setAgeWithStr(String age) {
        String parsedAge = "00";
        if (!age.equals("소아")) {
            if (age.contains("60")) {
                parsedAge = age.replace("대 이상", "").trim();
            } else {
                parsedAge = age.replace("대", "").trim();
            }
        }
        Dlog.d(parsedAge);
        this.age = parsedAge;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSnsRefer() {
        return snsRefer;
    }

    public void setSnsRefer(String snsRefer) {
        this.snsRefer = snsRefer;
    }


    public ShowTypeEnum getShowTypeEnum() {
        if (age.equals("00")) {
            return BABY;
        }

        if (sex == UserModel.GENDER_MAN) {
            return MAN;
        } else if (sex == UserModel.GENDER_WOMAN) {
            return WOMAN;
        }

        return null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMemOs() {
        return memOs;
    }

    public void setMemOs(String memOs) {
        this.memOs = memOs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.name);
        dest.writeInt(this.sex);
        dest.writeString(this.age);
        dest.writeLong(this.id);
        dest.writeString(this.job);
        dest.writeString(this.snsRefer);
        dest.writeInt(this.nprogAgree);
        dest.writeString(this.regDate);
        dest.writeString(this.nprogDate);
        dest.writeString(this.token);
        dest.writeString(this.memOs);
    }

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        this.email = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.sex = in.readInt();
        this.age = in.readString();
        this.id = in.readLong();
        this.job = in.readString();
        this.snsRefer = in.readString();
        this.nprogAgree = in.readInt();
        this.regDate = in.readString();
        this.nprogDate = in.readString();
        this.token = in.readString();
        this.memOs = in.readString();
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
