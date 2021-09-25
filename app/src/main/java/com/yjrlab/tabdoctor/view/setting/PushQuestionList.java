package com.yjrlab.tabdoctor.view.setting;

import android.os.Parcel;
import android.os.Parcelable;

import com.yjrlab.tabdoctor.model.PushQuestionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeonjukim on 2017. 6. 12..
 */

public class PushQuestionList implements Parcelable {
    public ArrayList<PushQuestionModel> all;

    public PushQuestionList() {
        all = new ArrayList<PushQuestionModel>();
    }

    public void add(List<PushQuestionModel> a) {
        for (int i = 0; i < a.size(); i++) {
            all.add(a.get(i));
        }
    }

    public static final Creator<PushQuestionList> CREATOR = new Creator<PushQuestionList>() {
        @Override
        public PushQuestionList createFromParcel(Parcel in) {
            return new PushQuestionList(in);
        }

        @Override
        public PushQuestionList[] newArray(int size) {
            return new PushQuestionList[size];
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

    private PushQuestionList(Parcel source) {
        // 요부분 2
        all = new ArrayList<PushQuestionModel>();
        source.readTypedList(all, PushQuestionModel.CREATOR);
    }


}
