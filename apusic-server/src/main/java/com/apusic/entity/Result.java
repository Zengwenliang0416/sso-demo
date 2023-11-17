package com.apusic.entity;

import org.springframework.stereotype.Service;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月16日 10:13:10
 * @packageName com.apusic.entity
 * @className Result
 * @describe TODO
 */
@Service
public class Result {
    private LoginInfo loginInfo;

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
