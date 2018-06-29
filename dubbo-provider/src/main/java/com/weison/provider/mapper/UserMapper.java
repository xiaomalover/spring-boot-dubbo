package com.weison.provider.mapper;

import com.weison.base.domain.User;
import java.util.List;

public interface UserMapper {

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> queryAll();

    User selectByUsername(String username);

    User selectByMobile(String mobile);

    User selectByUsernameOrMobile(String account);
}