package com.yjrlab.tabdoctor.network.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 6..
 */

public enum HealthSearchField {

    @SerializedName("hd_name")
    NAME,
    @SerializedName("hd_symptom")
    SYMPTOM,
    @SerializedName("hd_memdical_title")
    MEMDICAL_TITLE,
    @SerializedName("hd_content")
    CONTENT,
    @SerializedName("mo_name")
    DEPARTMENT;

}
