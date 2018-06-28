package com.weison.consumer.controller;

import org.springframework.validation.BindingResult;

public class BaseController {

    @SuppressWarnings("WeakerAccess")
    public String getValidateErrorMsg(BindingResult result) {
        StringBuffer errorMsg = new StringBuffer();
        result.getAllErrors().forEach((err) -> {
            if (errorMsg.length() > 0) {
                errorMsg.append(";");
            }
            errorMsg.append(err.getDefaultMessage());
        });
        return errorMsg.toString();
    }
}
