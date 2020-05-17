package com.whty.xqt.bean;

/**
 * @author wzj
 * 版本：1.0
 * 创建日期：2020/4/30 8:54
 * 描述：
 */
public class Response <T>{
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public Response<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }
}
