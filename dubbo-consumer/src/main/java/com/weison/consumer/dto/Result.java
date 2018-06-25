package com.weison.consumer.dto;

import com.alibaba.fastjson.JSON;
import com.weison.consumer.constant.ResponseCodeEnum;

/**
 * 请求返回数据对象
 * @param <T>
 */
public class Result<T> {

    private int code;

    private String msg;

    private T data;

    public Result(ResponseCodeEnum e, T data) {
        this.code = e.getCode();
        this.msg = e.getMsg();
        this.data = data;
    }

    public Result(ResponseCodeEnum e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
