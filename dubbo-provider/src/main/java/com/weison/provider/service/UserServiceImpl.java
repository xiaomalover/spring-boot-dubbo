package com.weison.provider.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.weison.base.constant.ResponseCodeEnum;
import com.weison.base.dto.Result;
import com.weison.provider.mapper.UserMapper;
import com.weison.base.model.User;
import com.weison.base.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "SpringJavaAutowiringInspection"})
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    /**
     * 用户注册
     * @param user 用户实体
     * @return Object
     */
    @Override
    @ResponseBody
    public Object register(User user) {

        User exist;
        exist = this.findByUsername(user.getUsername());
        if (ObjectUtil.isNotNull(exist)) {
            return new Result<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "用户名已被注册").toJson();
        }

        exist = this.findByMobile(user.getMobile());
        if (ObjectUtil.isNotNull(exist)) {
            return new Result<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "手机号已被注册").toJson();
        }

        if(user.getUsername().length() == 0) {
            return new Result<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "用户名不能为空").toJson();
        }

        if(user.getMobile().length() == 0) {
            return new Result<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "手机号不能为空").toJson();
        }

        if(user.getPassword().length() == 0) {
            return new Result<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "密码不能为空").toJson();
        }

        if (userMapper.insert(user) > 0) {
            return new Result<>(ResponseCodeEnum.HTTP_OK).toJson();
        } else {
            return new Result<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "注册失败").toJson();
        }
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.queryAll();
    }

    @Override
    public User findById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User findByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }
}
