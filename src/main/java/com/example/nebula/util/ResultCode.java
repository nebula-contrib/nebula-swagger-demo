package com.example.nebula.util;

/**
 * @Descriptin:
 * @ClassName: ResultCode
 */

/**
 * 通用返回状态码
 */
public enum ResultCode {
    SUCCESS(10000,"成功"),
    FAIL(1,"失败"),
    UNKNOWN_ERROR(-1,"未知错误"),
    NO_DATA(10001,"暂无数据");

    private final Integer code;
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
