package com.apusic.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.apusic.entity.LoginInfo;
import com.apusic.entity.Response;
import com.apusic.entity.Result;
import com.apusic.service.Validation;
import com.apusic.utils.RandomRequestIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("/")
    public String apusicPage(HttpSession session, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies==null){
            return "redirect:" + ssoServerUrl + "?redirect_url=http://apusic.jd.com:8081/";
        }
        for (Cookie cookie:cookies){
            if (Validation.validation(cookie)){
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
            }else {

            }
        }
        return "apusic-index";
    }


    @GetMapping("/apusic")
    public String apusic(Model model, HttpSession session, @RequestParam(value = "token", required = false) String token) {
        if (!StringUtils.isEmpty(token)) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> forEntity = restTemplate.getForEntity("http://cloudship.com:8080/userInfo?token=" + token, String.class);
            String body = forEntity.getBody();
            session.setAttribute("loginUser", body);
        }
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:" + ssoServerUrl + "?redirect_url=http://apusic.com:8081/apusic";
        } else {
            List<String> temp = new ArrayList<>();
            temp.add("zwl");
            temp.add("abc");
            model.addAttribute("temp", temp);
            return "apusic-index";
        }
    }
}
