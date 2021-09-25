package com.yjrlab.tabdoctor.view.setting;

import android.os.Parcel;
import android.os.Parcelable;

import com.yjrlab.tabdoctor.model.BodyPartSymptom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeonjukim on 2017. 6. 12..
 */

public class BodyPartSymptomList implements Parcelable {
    public ArrayList<BodyPartSymptom> all;

    public BodyPartSymptomList() {
        all = new ArrayList<BodyPartSymptom>();
    }

    public void add(List<BodyPartSymptom> a) {
        for (int i = 0; i < a.size(); i++) {
            all.add(a.get(i));
        }
    }

    public static final Creator<BodyPartSymptomList> CREATOR = new Creator<BodyPartSymptomList>() {
        @Override
        public BodyPartSymptomList createFromParcel(Parcel in) {
            return new BodyPartSymptomList(in);
        }

        @Override
        public BodyPartSymptomList[] newArray(int size) {
            return new BodyPartSymptomList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 요부분 1
        dest.writeTypedList(all);
    }

    private BodyPartSymptomList(Parcel source) {
        // 요부분 2
        all = new ArrayList<BodyPartSymptom>();
        source.readTypedList(all, BodyPartSymptom.CREATOR);
    }


}
