package com.yjrlab.tabdoctor.network.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jongrakmoon on 2017. 6. 6..
 */

public enum QuestionSearchField {
    @SerializedName("mpq_id")
    ID,
    @SerializedName("mem_id")
    MEM_ID,
    @SerializedName("mpq_body")
    DEPARTMENT,
    @SerializedName("mpq_answer")
    ANSWER,
    @SerializedName("mpq_datetime")
    DATE;

}
