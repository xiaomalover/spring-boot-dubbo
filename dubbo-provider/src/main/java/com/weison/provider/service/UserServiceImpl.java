package com.weison.provider.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.weison.base.api.UserService;
import com.weison.base.constant.ResponseCodeEnum;
import com.weison.base.dto.ResultResponse;
import com.weison.base.dto.UserModel;
import com.weison.base.dto.UserRegisterRequest;
import com.weison.provider.model.User;
import com.weison.base.dto.UserLoginRequest;
import com.weison.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "SpringJavaAutowiringInspection"})
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 验证器
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户实体
     * @return Object
     */
    @Override
    public Object register(UserRegisterRequest userRegisterRequest) {

        //根据模型中的规则，验证数据的合法性
        String errorMsg = this.getValidateErrorMsg(userRegisterRequest);
        if (errorMsg.length() > 0) {
            return new ResultResponse<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), errorMsg).toJson();
        }

        //根据业务，验证数据合法性
        User exist;
        exist = userMapper.selectByUsername(userRegisterRequest.getUsername());
        if (ObjectUtil.isNotNull(exist)) {
            return new ResultResponse<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "用户名已被注册").toJson();
        }
        exist = userMapper.selectByMobile(userRegisterRequest.getMobile());
        if (ObjectUtil.isNotNull(exist)) {
            return new ResultResponse<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "手机号已被注册").toJson();
        }


        User user = new User();
        user.setMobile(userRegisterRequest.getMobile());
        user.setEmail(userRegisterRequest.getEmail());
        user.setUsername(userRegisterRequest.getUsername());
        //密码加密
        user.setPassword(this.encodePassword(userRegisterRequest.getPassword()));
        //写入创建时间 TODO 自动处理
        String timestamp = String.valueOf(System.currentTimeMillis());
        int length = timestamp.length();
        int now = Integer.valueOf(timestamp.substring(0,length-3));
        user.setCreatedAt(now);


        if (userMapper.insert(user) > 0) {
            return new ResultResponse<>(ResponseCodeEnum.HTTP_OK).toJson();
        } else {
            return new ResultResponse<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "注册失败").toJson();
        }
    }

    @Override
    public Object login(UserLoginRequest userLoginRequest) {
        //根据模型中的规则，验证数据的合法性
        String errorMsg = this.getValidateErrorMsg(userLoginRequest);
        if (errorMsg.length() > 0) {
            return new ResultResponse<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), errorMsg).toJson();
        }

        //查询用户存不存在
        User user = userMapper.selectByUsernameOrMobile(userLoginRequest.getAccount());
        if (ObjectUtil.isNull(user)) {
            return new ResultResponse<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "用户名不正确").toJson();
        }

        //校验密码
        if (!this.encodePassword(userLoginRequest.getPassword()).equals(user.getPassword())) {
            return new ResultResponse<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "密码错误").toJson();
        }

        //转domain到dto
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setMobile(user.getMobile());
        userModel.setEmail(user.getEmail());
        userModel.setUsername(user.getUsername());
        userModel.setCreatedAt(user.getCreatedAt());
        userModel.setUpdatedAt(user.getUpdatedAt());
        userModel.setStatus(user.getStatus());

        return new ResultResponse<>(ResponseCodeEnum.HTTP_OK, userModel).toJson();
    }

    /**
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     */
    @Override
    public List<UserModel> getUserList(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.queryAll();
        List<UserModel> userModels = new ArrayList<>();
        users.forEach((user) -> {
            UserModel userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setMobile(user.getMobile());
            userModel.setEmail(user.getEmail());
            userModel.setUsername(user.getUsername());
            userModel.setStatus(user.getStatus());
            userModel.setCreatedAt(user.getCreatedAt());
            userModel.setUpdatedAt(user.getUpdatedAt());
            userModels.add(userModel);
        });
        return userModels;
    }

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return 用户数据
     */
    @Override
    public UserModel getUser(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setMobile(user.getMobile());
        userModel.setEmail(user.getEmail());
        userModel.setUsername(user.getUsername());
        userModel.setStatus(user.getStatus());
        userModel.setCreatedAt(user.getCreatedAt());
        userModel.setUpdatedAt(user.getUpdatedAt());
        return userModel;
    }


    /**
     * 加密登录密码
     * @param password 登录密码名文
     * @return String
     */
    private String encodePassword(String password) {
        return SecureUtil.md5(SecureUtil.md5(password));
    }

    /**
     * 获取验证错误信息
     * @param obj 待验证对象
     * @return String
     */
    private String getValidateErrorMsg(Object obj) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
        StringBuffer errorMsg = new StringBuffer();
        constraintViolations.forEach((v) -> {
            if (errorMsg.length() > 0) {
                errorMsg.append(";");
            }
            errorMsg.append(v.getMessage());
        });
        return errorMsg.toString();
    }
}
