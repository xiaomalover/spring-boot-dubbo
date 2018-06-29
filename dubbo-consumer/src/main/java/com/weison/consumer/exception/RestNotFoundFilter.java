package com.weison.consumer.exception;

import com.weison.base.dto.Result;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 * 404异常捕获
 */
@RestController
public class RestNotFoundFilter implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Object handleError() {
        return new Result<>(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
