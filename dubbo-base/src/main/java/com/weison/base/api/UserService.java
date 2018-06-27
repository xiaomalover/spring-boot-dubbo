package com.weison.base.api;

import com.weison.base.model.User;
import java.util.List;

/**
 * 用户接口类
 */
public interface UserService {

    /**
     * 添加用户
     * @param user 用户实体
     * @return User
     */
    Object register(User user);

    /**
     * 查所有用户
     * @return List <User>
     */
    List <User> findAllUser(int pageNum, int pageSize);

    /**
     *
     * @return User
     */
    User findById(int id);

    /**
     * 查单个用户
     * @return User
     */
    User findByUsername(String username);

    /**
     * 查单个用户
     * @return User
     */
    User findByMobile(String mobile);
}
