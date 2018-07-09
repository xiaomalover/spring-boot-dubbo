package com.weison.consumer.exception;

import com.weison.base.constant.ResponseCodeEnum;
import com.weison.base.dto.ResultResponse;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 * 错误捕获
 */
@RestController
public class RestErrorFilter implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public Object handleError(HttpServletResponse response) {
        int code = response.getStatus();
        switch (code) {
            case HttpServletResponse.SC_UNAUTHORIZED:
                response.setStatus(HttpStatus.OK.value());
                return new ResultResponse<>(ResponseCodeEnum.HTTP_UNAUTHORIZED);
            default:
                return new ResultResponse<>(ResponseCodeEnum.HTTP_NOT_FOUND);
        }
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
