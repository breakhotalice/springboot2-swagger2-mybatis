package com.snoopy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户")
public class UserDomain {

    @ApiModelProperty(value = "用户编号", required = true, example = "123")
    private Integer userId;

    @JsonIgnore
    @ApiModelProperty(value = "用户名", required = true, example = "snoopy")
    private String userName;

    @ApiModelProperty(value = "密码", required = true, example = "12354")
    private String password;

    @ApiModelProperty(value = "电话", required = true, example = "18820276003")
    private String phone;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    @Override
    public String toString() {
        return "UserDomain [userId=" + userId + ", userName=" + userName + ", password=" + password + ", phone=" + phone
            + "]";
    }

}