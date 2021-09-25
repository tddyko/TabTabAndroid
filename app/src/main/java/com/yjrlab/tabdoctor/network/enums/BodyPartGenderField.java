package com.yjrlab.tabdoctor.network.enums;

import com.google.gson.annotations.SerializedName;
import com.yjrlab.tabdoctor.view.setting.ShowTypeEnum;

/**
 * Created by jongrakmoon on 2017. 6. 8..
 */

public enum BodyPartGenderField {
    @SerializedName("M")MAN,
    @SerializedName("F")WOMAN,
    @SerializedName("C")CHILDREN;

    public static BodyPartGenderField parse(ShowTypeEnum showTypeEnum) {
        if (showTypeEnum == ShowTypeEnum.MAN) {
            return MAN;
        } else if (showTypeEnum == ShowTypeEnum.WOMAN) {
            return WOMAN;
        } else if (showTypeEnum == ShowTypeEnum.BABY) {
            return CHILDREN;
        }
        return null;
    }
}
