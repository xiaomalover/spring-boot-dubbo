package com.weison.base.dto;

import com.alibaba.fastjson.JSON;
import com.weison.base.constant.ResponseCodeEnum;
import java.io.Serializable;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 * 请求返回数据对象
 * @param <T>
 */
public class ResultResponse<T> implements Serializable {

    private static final long serialVersionUID = -3062498620783165474L;

    private int code;

    private String msg;

    private T data;

    public ResultResponse(ResponseCodeEnum e, T data) {
        this.code = e.getCode();
        this.msg = e.getMsg();
        this.data = data;
    }

    public ResultResponse(ResponseCodeEnum e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public ResultResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultResponse(int code, String msg) {
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

    public Object toJson() {
        return JSON.toJSON(this);
    }
}
