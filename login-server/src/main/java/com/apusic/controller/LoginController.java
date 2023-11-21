package com.apusic.controller;

import com.apusic.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月16日 16:31:23
 * @packageName com.apusic.controller
 * @className LoginController
 * @describe TODO
 */
@Controller
public class LoginController {

    @GetMapping("/getPage")
    public String getPage() {
        return "page";
    }
    /**
     * @param url
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "redirect_url") String url, Model model, @RequestParam(value = "cookie",required = false) String cookie) {
        // 判断Cookie中是否保存了令牌
        // 有令牌表明之前已经登录过了，直接回到之前的页面并带上令牌信息
        if (!StringUtils.isEmpty(cookie)) {
            return "redirect:" + url + "?cookie=" + cookie;
        }
        model.addAttribute("url", url);
        return "login";
    }

    /**
     * 登陆按钮触发的方法，此时该方法会重定位到之前的页面
     *
     * @param username 用户名
     * @param password 密码
     * @param url      重定向的url地址
     * @param response HTTP响应
     *
     * @return
     */
    @PostMapping("/doLogin")
    public String doLogin(String username, String password, String url, HttpServletRequest request, HttpServletResponse response) {
        // 这里模拟登录成功，只要账号和密码不为空，即登陆成功
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            // 使用UUID创建一个令牌
            String uuid = UUID.randomUUID().toString().replace("-", "");
            // 同时将令牌保存到Cookie中
            CookieUtils.setCookie(request,response,username,uuid,0,"UTF-8");
//            Cookie cookie = new Cookie(username, uuid);
//            cookie.setDomain(".jd.com");
//            cookie.setPath("/");
//            response.addCookie(cookie);
            // 回到之前的页面并带上令牌信息
            return "redirect:" + url;
        } else {
            // 登录失败，跳转到登录页面
            return "login";
        }
    }
}
