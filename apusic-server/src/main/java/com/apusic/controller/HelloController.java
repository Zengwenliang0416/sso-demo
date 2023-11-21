package com.apusic.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.apusic.entity.LoginInfo;
import com.apusic.entity.Response;
import com.apusic.entity.Result;
import com.apusic.service.Validation;
import com.apusic.utils.RandomRequestIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月17日 09:10:29
 * @packageName com.apusic.controller
 * @className HelloController
 * @describe TODO
 */
@Controller
public class HelloController {
    @Value("${sso.server.url}")
    private String ssoServerUrl;

    @GetMapping("/")
    public String apusicPage(HttpSession session, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (Validation.validation(cookie) != null) {
                String loginName = cookie.getName();
                LoginInfo loginInfo = new LoginInfo();
                Result result = new Result();
                Response response = new Response();
                loginInfo.setAdminPin("admin");
                loginInfo.setLoginUrl("");
                loginInfo.setLoginName(loginName);
                loginInfo.setPin("test");
                loginInfo.setType(2);
                response.setRequestId(RandomRequestIdGenerator.generateRandomRequestId());
                result.setLoginInfo(loginInfo);
                response.setResult(result);
                return JSON.toJSONString(response, SerializerFeature.WriteMapNullValue);
            } else {
//                return "redirect:" + ssoServerUrl + "?redirect_url=http://apusic.jd.com:8081/";
                return "redirect:" + ssoServerUrl;
            }
        }
        return "index";
    }

    @GetMapping("/apusic")
    public String apusic(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("123")) {
                session.setAttribute("username",cookie.getName());
                return "index";
            }
        }
        return "redirect:" + ssoServerUrl + "?redirect_url=http://apusic.jd.com:8081/apusic";
    }
}
