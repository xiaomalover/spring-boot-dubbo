package com.weison.consumer.controller;

import com.weison.base.api.UserService;
import com.weison.base.model.User;
import com.weison.consumer.constant.ResponseCodeEnum;
import com.weison.consumer.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserService userService;

    @RequestMapping("/insert")
    public Object addUser(User user) {
        Integer num = userService.addUser(user);
        if (num > 0) {
            return new Result<>(ResponseCodeEnum.HTTP_OK);
        } else {
            return new Result<>(ResponseCodeEnum.NORMAL_RETURN_ERROR.getCode(), "添加失败");
        }
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
        return new Result<>(ResponseCodeEnum.HTTP_OK, userService.findOneUser(userId));
    }
}