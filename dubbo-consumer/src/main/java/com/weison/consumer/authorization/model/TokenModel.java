package com.weison.consumer.authorization.model;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * @author xiaomalover <xiaomalover@gmail.com>
 */
public class TokenModel {

    /**
     * 用户id
     */
    private int userId;

    /**
     * 随机生成的uuid
     */
    private String token;

    /**
     * 过期时间
     */
    private int ttl;

    public TokenModel() {
    }

    public TokenModel(int userId, String token, int ttl) {
        this.userId = userId;
        this.token = token;
        this.ttl = ttl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
