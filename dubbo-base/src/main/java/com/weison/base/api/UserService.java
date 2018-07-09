package com.weison.base.api;

import com.weison.base.dto.UserModel;
import com.weison.base.dto.UserLoginRequest;
import com.weison.base.dto.UserRegisterRequest;
import java.util.List;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 * 用户接口类
 */
public interface UserService {

    /**
     * 用户注册
     * @param userRegisterRequest 注册实体
     * @return User
     */
    Object register(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     * @param userLoginRequest 登录实体
     * @return Object
     */
    Object login(UserLoginRequest userLoginRequest);

    /**
     * 查所有用户
     * @param pageNum 页码数
     * @param pageSize 每页条数
     * @return List <UserModel>
     */
    List <UserModel> getUserList(int pageNum, int pageSize);

    /**
     * 查询一个用户
     * @param userId 用户ID
     * @return UserModel
     */
    UserModel getUser(int userId);
}
