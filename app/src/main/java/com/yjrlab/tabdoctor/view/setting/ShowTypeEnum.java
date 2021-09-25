package com.yjrlab.tabdoctor.view.setting;

import com.yjrlab.tabdoctor.network.enums.BodyPartGenderField;

/**
 * Created by yeonjukim on 2017. 6. 7..
 */

public enum ShowTypeEnum {
    WOMAN("F"), MAN("M"), BABY("C");

    final private String type;

    private ShowTypeEnum(String type) { //enum에서 생성자 같은 역할
        this.type = type;
    }

    public String getType() { // 문자를 받아오는 함수
        return type;
    }

    public BodyPartGenderField toGenderField() {
        if (this == WOMAN) {
            return BodyPartGenderField.WOMAN;
        } else if (this == MAN) {
            return BodyPartGenderField.MAN;
        } else if (this == BABY) {
            return BodyPartGenderField.CHILDREN;
        }
        return null;
    }


    public static ShowTypeEnum parse(String type) {
        if (WOMAN.type.equals(type)) {
            return WOMAN;
        } else if (MAN.type.equals(type)) {
            return MAN;
        } else if (BABY.type.equals(type)) {
            return BABY;
        }
        return null;
    }

}
