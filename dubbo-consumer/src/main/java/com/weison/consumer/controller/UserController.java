package com.weison.consumer.controller;

import com.weison.base.api.UserService;
import com.weison.base.model.User;
import com.weison.base.constant.ResponseCodeEnum;
import com.weison.base.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@RestController
@RequestMapping(value = "user")
public class UserController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Object register(@Valid User user, BindingResult result) {

        //验证数据合法性，虽然生产者服务里有验证，但这里也加一个，可以把不合法数据挡在消费者处，减轻生产者服务的压力
        if (result.hasErrors()) {
            StringBuffer errorMsg = new StringBuffer();
            result.getAllErrors().forEach((err) -> {
                if (errorMsg.length() > 0) {
                    errorMsg.append(";");
                }
                errorMsg.append(err.getDefaultMessage());
            });
            return new Result<>(ResponseCodeEnum.NORMAL_RETURN_ERROR, errorMsg.toString());
        }

        return userService.register(user);
    }

    @GetMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return new Result<>(
                ResponseCodeEnum.HTTP_OK,
                userService.findAllUser(pageNum, pageSize)
        );
    }

    @GetMapping(value = "/{userId}", produces = {"application/json;charset=UTF-8"})
    public Object findOneUser(@PathVariable("userId") int userId) {
        return new Result<>(ResponseCodeEnum.HTTP_OK, userService.findById(userId));
    }
}