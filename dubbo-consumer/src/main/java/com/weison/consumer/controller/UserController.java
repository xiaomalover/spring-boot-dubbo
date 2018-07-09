package com.weison.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.weison.base.api.UserService;
import com.weison.base.dto.UserModel;
import com.weison.base.constant.ResponseCodeEnum;
import com.weison.base.dto.ResultResponse;
import com.weison.base.dto.UserLoginRequest;
import com.weison.base.dto.UserRegisterRequest;
import com.weison.consumer.authorization.annotation.Authorization;
import com.weison.consumer.authorization.annotation.CurrentUser;
import com.weison.consumer.authorization.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 * 用户控制器
 */
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@RestController
@RequestMapping(value = "user")
public class UserController extends BaseController{

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/register")
    public Object register(@Valid UserRegisterRequest userRegisterRequest, BindingResult result) {

        //验证数据合法性，虽然生产者服务里有验证，但这里也加一个，可以把不合法数据挡在消费者处，减轻生产者服务的压力
        String errMsg = super.getValidateErrorMsg(result);
        if (errMsg.length() > 0) {
            return new ResultResponse<>(ResponseCodeEnum.NORMAL_RETURN_ERROR, errMsg);
        }

        return userService.register(userRegisterRequest);
    }

    @PostMapping("/login")
    public Object login(@Valid UserLoginRequest userLoginRequest, BindingResult result) {

        //验证数据合法性，虽然生产者服务里有验证，但这里也加一个，可以把不合法数据挡在消费者处，减轻生产者服务的压力
        String errMsg = super.getValidateErrorMsg(result);
        if (errMsg.length() > 0) {
            return new ResultResponse<>(ResponseCodeEnum.NORMAL_RETURN_ERROR, errMsg);
        }

        //走服务端登录服务
        JSONObject jsonObject = (JSONObject) userService.login(userLoginRequest);
        ResultResponse res  = jsonObject.toJavaObject(ResultResponse.class);
        if (res.getCode() == ResponseCodeEnum.HTTP_OK.getCode()) {
            //服务端返回成功存入TOKEN
            JSONObject jsonUser = (JSONObject) res.getData();
            UserModel userModel = jsonUser.toJavaObject(UserModel.class);
            return new ResultResponse<>(ResponseCodeEnum.HTTP_OK, tokenManager.createToken(userModel.getId()));
        }
        return res;
    }

    @GetMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return new ResultResponse<>(
                ResponseCodeEnum.HTTP_OK,
                userService.getUserList(pageNum, pageSize)
        );
    }

    @Authorization
    @GetMapping(value = "/info", produces = {"application/json;charset=UTF-8"})
    public Object userInfo(@CurrentUser UserModel userModel) {
        return new ResultResponse<>(ResponseCodeEnum.HTTP_OK, userModel);
    }
}