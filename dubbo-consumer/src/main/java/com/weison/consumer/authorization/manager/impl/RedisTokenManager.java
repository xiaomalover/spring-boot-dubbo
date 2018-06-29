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


    @Override
    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }

        String tokenKey = this.getTokenKey(model.getToken());

        RBucket<String> t = RedissonUtil.getRBucket(redisson, tokenKey);

        System.out.println(t.get());

        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        //TODO 续期TOKEN
        return true;
    }

    @Override
    public void deleteToken(String token) {
        //删除token
        String tokenKey = this.getTokenKey(token);
        RBucket<Object> t = RedissonUtil.getRBucket(redisson, tokenKey);
        t.delete();
    }

    /**
     * @param token Token
     * @return String
     */
    private String getTokenKey(String token) {
        return TokenConstant.TOKEN_KEY_PREFIX + token;
    }

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
