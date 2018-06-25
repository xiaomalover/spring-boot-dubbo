package com.weison.provider.mapper;

import com.weison.base.model.User;

import java.util.List;

public interface UserMapper {

    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    List<User> queryAll();
}