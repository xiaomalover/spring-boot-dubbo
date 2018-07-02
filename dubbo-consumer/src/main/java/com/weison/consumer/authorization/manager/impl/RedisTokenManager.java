package com.weison.consumer.authorization.manager.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.weison.base.util.RedissonUtil;
import com.weison.consumer.authorization.constant.TokenConstant;
import com.weison.consumer.authorization.manager.TokenManager;
import com.weison.consumer.authorization.model.TokenModel;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 * @see com.weison.consumer.authorization.manager.TokenManager
 * @author xiaomalover <xiaomalover@gmail.com>
 */
@Component
public class RedisTokenManager implements TokenManager {

    @SuppressWarnings({"SpringJavaAutowiringInspection", "SpringAutowiredFieldsWarningInspection"})
    @Autowired
    RedissonClient redisson;

    /**
     * 创建TOKEN
     * @param userId 指定用户的id
     * @return TokenModel
     */
    @Override
    public TokenModel createToken(int userId) {

        //存TOKEN
        String token = SecureUtil.md5(UUID.randomUUID().toString());
        TokenModel tokenModel = new TokenModel(userId, token, TokenConstant.TOKKEN_EXPIRED);
        String tokenKey = this.getTokenKey(token);
        RBucket<Object> t = RedissonUtil.getRBucket(redisson, tokenKey);
        t.set(tokenModel, TokenConstant.TOKKEN_EXPIRED, TimeUnit.SECONDS);

        //存TOKEN记录
        this.handHash(userId, token);

        return tokenModel;
    }

    /**
     * 校验TOKEN
     * @param token token
     * @return result
     */
    @Override
    public int checkToken(String token) {
        String tokenKey = this.getTokenKey(token);
        RBucket<Object> t = RedissonUtil.getRBucket(redisson, tokenKey);
        TokenModel j = (TokenModel) t.get();
        if (ObjectUtil.isNotNull(j)) {
            //校验通过，续期TOKEN
            j.setTtl(TokenConstant.TOKKEN_EXPIRED);
            t.set(j, TokenConstant.TOKKEN_EXPIRED, TimeUnit.SECONDS);
            return j.getUserId();
        }
        return  0;
    }

    /**
     * 删除TOKEN
     * @param token 用户token
     */
    @Override
    public void deleteToken(String token) {
        //删除token
        String tokenKey = this.getTokenKey(token);
        RBucket<Object> t = RedissonUtil.getRBucket(redisson, tokenKey);
        t.delete();
    }

    /**
     * 获取Token的存储键
     * @param token Token
     * @return String
     */
    private String getTokenKey(String token) {
        return TokenConstant.TOKEN_KEY_PREFIX + token;
    }

    /**
     * 处理TOKEN与用户ID的映射，以便在二次登录时清楚除旧的token
     * @param userId 用户id
     * @param token Token
     */
    private void handHash(Integer userId, String token) {
        String hashKey = TokenConstant.TOKEN_HASH_KEY_PREFIX + SecureUtil.md5(userId.toString()).substring(0, 2);
        RMap<Integer, String> map = RedissonUtil.getRMap(redisson, hashKey);

        String exist = map.get(userId);
        if (ObjectUtil.isNotNull(exist)) {
            this.deleteToken(exist);
        }

        map.put(userId, token);
    }

}
