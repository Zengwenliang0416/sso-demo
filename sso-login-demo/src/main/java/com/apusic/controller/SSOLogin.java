package com.apusic.controller;

import com.apusic.service.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.security.auth.message.MessageInfo;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月21日 16:39:39
 * @packageName com.apusic.controller
 * @className SSOLogin
 * @describe TODO
 */
@Controller
public class SSOLogin {
    @Autowired
    private Validation validation;

    @GetMapping("/login")
    public String loginPage(HttpSession session, HttpServletRequest request) {
//        return "login";
        return "redirect:http://login.jd.com:8081/getPage";
    }

    @GetMapping("/redirect")
    public void redirectPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String loginUrl = "http://login.jd.com:8081/getPage";
        validation.redirectToLoginPage(loginUrl, response);
//        String loginUrl = "/page.html";
//        RequestDispatcher dispatcher = request.getRequestDispatcher(loginUrl);
//        try {
//            dispatcher.forward(request,response);
//        } catch (ServletException e) {
//            throw new RuntimeException(e);
//        }
    }

}
