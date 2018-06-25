package com.weison.consumer.exception;

import com.weison.consumer.constant.ResponseCodeEnum;
import com.weison.consumer.dto.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object requestNotReadable() {
        return new Result<>(ResponseCodeEnum.HTTP_INTERNAL_SERVER_ERROR);
    }
}