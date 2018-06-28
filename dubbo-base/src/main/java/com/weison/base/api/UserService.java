package com.weison.base.api;

import com.weison.base.model.User;
import com.weison.base.model.form.LoginForm;
import java.util.List;

/**
 * 用户接口类
 */
public interface UserService {

    /**
     * 用户注册
     * @param user 用户实体
     * @return User
     */
    Object register(User user);

    /**
     * 用户登录
     * @param loginForm 登录实体
     * @return Object
     */
    Object login(LoginForm loginForm);

    /**
     * 查所有用户
     * @param pageNum 页码数
     * @param pageSize 每页条数
     * @return List <User>
     */
    List <User> findAllUser(int pageNum, int pageSize);

    /**
     * 根据用户主键查单个用户
     * @param id 用户主键
     * @return User
     */
    User findById(int id);

    /**
     * 根据用户名查单个用户
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);

    /**
     * 根据手机号查单个用户
     * @param mobile 手机号
     * @return User
     */
    User findByMobile(String mobile);

    /**
     * 根据用户名或手机号查单个用户
     * @param account 用户名或手机号
     * @return User
     */
    User findByUsernameOrMobile(String account);
}
