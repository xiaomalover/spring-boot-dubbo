package com.weison.base.model.form;

import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 */
public class LoginForm implements Serializable {

    @NotBlank(message = "帐号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
