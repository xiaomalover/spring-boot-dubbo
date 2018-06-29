package com.weison.consumer.authorization.manager;

import com.weison.consumer.authorization.model.TokenModel;

/**
 * 对Token进行操作的接口
 * @author xiaomalover <xiaomalover@gmail.com>
 */
public interface TokenManager {

    /**
     * 创建一个token关联上指定用户
     * @param userId 指定用户的id
     * @return 生成的token
     */
    public TokenModel createToken(int userId);

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    public boolean checkToken(TokenModel model);

    /**
     * 清除token
     * @param token 用户token
     */
    public void deleteToken(String token);

}
