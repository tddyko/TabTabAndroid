package com.yjrlab.tabdoctor.network.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 6..
 */

public enum HealthSortField {
    @SerializedName("hd_code")
    CODE,
    @SerializedName("hd_name")
    NAME,
    @SerializedName("hd_datetime")
    DATE;

}
