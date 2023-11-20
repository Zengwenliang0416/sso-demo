package com.apusic.service;

import javax.servlet.http.Cookie;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月17日 16:21:30
 * @packageName com.apusic.service
 * @className Validation
 * @describe TODO
 */
public class Validation {
    public static String validation(Cookie cookie) {
        String username = cookie.getName();
        return username;
    }
}
