package com.weison.consumer.exception;

import com.weison.base.constant.ResponseCodeEnum;
import com.weison.base.dto.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 * 异常捕获处理
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object requestNotReadable(Exception e) {
        return new Result<>(ResponseCodeEnum.HTTP_INTERNAL_SERVER_ERROR, e.getMessage());
    }
}