package com.example.nebula.util;

import lombok.Data;

/**
 * 通用返回类
 */
@Data
public class R<T> {
    /*返回体*/
    private Integer code;
    private String msg;
    private T data;

    /**
     * 成功
     **/
    public R success(Object object) {
        R result = new R();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static <T> R<T> data(Object object) {
        R result = new R();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    /**
     * 失败
     **/
    public R fail(Object object) {
        R result = new R();
        result.setCode(ResultCode.FAIL.getCode());
        result.setMsg(ResultCode.FAIL.getMsg());
        result.setData(object);
        return result;
    }

    public R result(Integer code, String msg, Object object) {
        R result = new R();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
}

