package com.yjrlab.tabdoctor.network.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 8..
 */

public enum BodyPartSearchField {

    @SerializedName("pob_id")ID,
    @SerializedName("pob_name")NAME,
    @SerializedName("pob_group")GROUP

//    R=“최초질문”, Y=“예”, N=“아니오”, A=“결과"
}
