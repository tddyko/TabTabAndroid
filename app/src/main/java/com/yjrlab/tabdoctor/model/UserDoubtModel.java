package com.yjrlab.tabdoctor.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jongrakmoon on 2017. 6. 6..
 */

public class UserDoubtModel extends UserModel {
    @SerializedName("doubt")
    private List<DoubtModel> doubts;

    public List<DoubtModel> getDoubts() {
        return doubts;
    }

    public void setDoubts(List<DoubtModel> doubts) {
        this.doubts = doubts;
    }

    public int size(){
        if(doubts==null){
            return 0;
        }else{
            return doubts.size();
        }
    }
}
