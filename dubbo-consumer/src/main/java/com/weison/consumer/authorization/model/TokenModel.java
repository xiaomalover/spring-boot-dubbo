package com.weison.consumer.authorization.model;

import java.io.Serializable;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * @author xiaomalover <xiaomalover@gmail.com>
 */
public class TokenModel implements Serializable {

    private static final long serialVersionUID = -3022498620763167474L;

    /**
     * 用户id
     */
    private long userId;

    /**
     * 随机生成的uuid
     */
    private String token;

    /**
     * 过期时间
     */
    private int ttl;

    public TokenModel(long userId, String toke, int ttl) {
        this.userId = userId;
        this.token = token;
        this.ttl = ttl;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}
