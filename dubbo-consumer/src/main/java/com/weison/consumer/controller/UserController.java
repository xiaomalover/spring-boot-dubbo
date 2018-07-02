package com.weison.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.weison.base.api.UserService;
import com.weison.base.domain.User;
import com.weison.base.constant.ResponseCodeEnum;
import com.weison.base.dto.Result;
import com.weison.base.model.form.LoginForm;
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
    public Object register(@Valid User user, BindingResult result) {

        //验证数据合法性，虽然生产者服务里有验证，但这里也加一个，可以把不合法数据挡在消费者处，减轻生产者服务的压力
        String errMsg = super.getValidateErrorMsg(result);
        if (errMsg.length() > 0) {
            return new Result<>(ResponseCodeEnum.NORMAL_RETURN_ERROR, errMsg);
        }

        return userService.register(user);
    }

    @PostMapping("/login")
    public Object login(@Valid LoginForm loginForm, BindingResult result) {

        //验证数据合法性，虽然生产者服务里有验证，但这里也加一个，可以把不合法数据挡在消费者处，减轻生产者服务的压力
        String errMsg = super.getValidateErrorMsg(result);
        if (errMsg.length() > 0) {
            return new Result<>(ResponseCodeEnum.NORMAL_RETURN_ERROR, errMsg);
        }

        //走服务端登录服务
        JSONObject jsonObject = (JSONObject) userService.login(loginForm);
        Result res  = jsonObject.toJavaObject(Result.class);
        if (res.getCode() == ResponseCodeEnum.HTTP_OK.getCode()) {
            //服务端返回成功存入TOKEN
            JSONObject jsonUser = (JSONObject) res.getData();
            User user = jsonUser.toJavaObject(User.class);
            return new Result<>(ResponseCodeEnum.HTTP_OK, tokenManager.createToken(user.getId()));
        }
        return res;
    }

    @GetMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return new Result<>(
                ResponseCodeEnum.HTTP_OK,
                userService.findAllUser(pageNum, pageSize)
        );
    }

    @Authorization
    @GetMapping(value = "/info", produces = {"application/json;charset=UTF-8"})
    public Object userInfo(@CurrentUser User user) {
        return new Result<>(ResponseCodeEnum.HTTP_OK, user);
    }
}