package com.apusic.entity;

import org.springframework.stereotype.Service;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月16日 10:12:34
 * @packageName com.apusic.entity
 * @className LoginInfo
 * @describe TODO
 */
@Service
public class LoginInfo {
    /**
     * 当前账号的父账号pin，当前用户存在父账号时才用该值
     */
    private String adminPin = "";
    /**
     * 当前账号用户名
     */
    private String loginName = "";
    /**
     * 跳转地址，未登录时要跳转的登陆地址
     */
    private String loginUrl = "http://{{云舰登陆地址}}";
    /**
     * 当前账号pin，typy为1或2时生效
     */
    private String pin = "";
    /**
     * 当前登陆身份类型，1-主账号，2-子账号，3-角色，0-未登录
     */
    private long type = 0;

    public String getAdminPin() {
        return adminPin;
    }

    public void setAdminPin(String adminPin) {
        this.adminPin = adminPin;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public long getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

