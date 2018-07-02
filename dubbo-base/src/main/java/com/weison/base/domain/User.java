package com.weison.base.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 * 用户数据库模型
 */
public class User implements Serializable{

    private static final long serialVersionUID = -3052498620783167474L;

    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 4, message = "用户名长度不能少于4位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 4, max = 20, message = "密码长度必须为4到20位")
    private String password;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp="^1[3456789]\\d{9}$",message="手机号格式不正确")
    private String mobile;

    private String email;

    private Integer status;

    private Integer createdAt;

    private Integer updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }
}
