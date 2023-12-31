package com.apusic.controller;

import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
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
    // 注入RedisTemplate
    @Autowired
    StringRedisTemplate redisTemplate;

    @ResponseBody
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("token") String token) {
        // 从Redis中，通过token获取用户信息
        String username = redisTemplate.opsForValue().get(token);
        return username;
    }


    /**
     * @param url
     * @param model
     * @param sso_token
     * @return
     */
    @GetMapping("/login.html")
    public String loginPage(@RequestParam("redirect_url") String url, Model model, @CookieValue(value = "sso_token", required = false) String sso_token) {
        // 判断Cookie中是否保存了令牌
        // 有令牌表明之前已经登录过了，直接回到之前的页面并带上令牌信息
        if (!StringUtils.isEmpty(sso_token)) {
            return "redirect:" + url + "?token=" + sso_token;
        }
        model.addAttribute("url", url);
        return "login";
    }


    /**
     * @param username
     * @param password
     * @param url
     * @param response
     * @return
     */
    @PostMapping("/doLogin")
    public String doLogin(String username, String password, String url, HttpServletResponse response) {
        // 这里模拟登录成功，只要账号和密码不为空，即登陆成功
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            // 使用UUID创建一个令牌
            String uuid = UUID.randomUUID().toString().replace("-", "");
            // Redis中以令牌为key，用户名为value进行存储
            redisTemplate.opsForValue().set(uuid, username);
            // 同时将令牌保存到Cookie中
            Cookie sso_token = new Cookie("sso_token", uuid);
            response.addCookie(sso_token);
            // 回到之前的页面并带上令牌信息
            return "redirect:" + url + "?token=" + uuid;
        } else {
            // 登录失败，跳转到登录页面
            return "login";
        }
    }
}
