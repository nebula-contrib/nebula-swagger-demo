package com.example.nebula.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConditionEnum {


    相等("EQUAL", "=="),
    包含("CONTAINS", "CONTAINS"),
    开始("STARTS", "STARTS WITH"),
    结束("ENDS", "ENDS WITH");

    private String name;
    private String code;

    public static String getCode(String name) {
        ConditionEnum[] values = ConditionEnum.values();
        for (ConditionEnum conditionEnum : values) {
            if (name.equalsIgnoreCase(conditionEnum.getName())) {
                return conditionEnum.getCode();
            }
        }
        return "";
    }
}
