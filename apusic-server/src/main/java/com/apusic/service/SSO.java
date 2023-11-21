package com.apusic.service;

import com.alibaba.fastjson.JSONObject;

import javax.security.auth.message.MessageInfo;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月20日 16:30:08
 * @packageName com.apusic.service
 * @className SSO
 * @describe TODO
 */
public class SSO {
    /**
     * 验证请求
     *
     * @param messageInfo
     */
    public void validateRequest(MessageInfo messageInfo) {
        HttpServletRequest request = (HttpServletRequest) messageInfo.getRequestMessage();
        HttpServletResponse response = (HttpServletResponse) messageInfo.getResponseMessage();
        Cookie[] cookies = request.getCookies();
        cookieValidation(cookies);
    }

    /**
     * 验证Cookie
     *
     * @param cookies 需要验证的Cookie数组
     */
    public static void cookieValidation(Cookie[] cookies) {
        try {
            // 验证Cookie
            JSONObject loginInfo = validateCookie(cookies);
            if (loginInfo.getInteger("type") == 0) {
                // 当前用户处于未登录状态，重定向到登陆页面
                String loginUrl = loginInfo.getString("loginUrl");
                redirectToLoginPage(loginUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证Cookie
     * 发送验证请求
     * 云舰应用访问方式：http://loginstate-inner.{{云舰域名后缀}}/v1/validation
     * 云舰svc访问方式：http://uc-loginstate-inner.jd-tpaas.svc/v1/validation
     *
     * @param cookies Cookie数组
     * @return 验证通过后的登录信息JSONObject对象
     * @throws IOException 如果验证失败则抛出异常
     */
    public static JSONObject validateCookie(Cookie[] cookies) throws IOException {
        URL url = new URL("http://uc-loginstate-inner.jd-tpaas.svc/v1/validation"); // 填入验证接口的URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        for (Cookie cookie : cookies) {
            connection.setRequestProperty("Cookie", cookie.getName() + "=" + cookie.getValue());
        }
        int responseCode = connection.getResponseCode();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            JSONObject loginInfo = JSONObject.parseObject(readResponse(connection))
                    .getJSONObject("result")
                    .getJSONObject("loginInfo");
            return loginInfo;
        } else {
            throw new IOException("Failed to get login URL. Response code: " + responseCode);
        }
    }

    /**
     * 重定向到登录页面
     *
     * @param loginUrl 登录页面URL
     * @throws IOException 如果重定向失败则抛出异常
     */
    private static void redirectToLoginPage(String loginUrl) throws IOException {
        // 重定向到登录页面
        URL url = new URL(loginUrl); // 创建一个URL对象，传入登录页面的URL地址
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // 打开URL连接
        connection.setRequestMethod("GET"); // 设置请求方法为GET
        connection.setInstanceFollowRedirects(false); // 设置为不自动跟随重定向
        connection.getResponseCode(); // 获取响应码
        String redirectUrl = connection.getHeaderField("Location"); // 获取重定向地址
        if (redirectUrl != null) {
            // 执行重定向
            URL redirect = new URL(redirectUrl); // 创建一个URL对象，传入重定向地址
            HttpURLConnection redirectConnection = (HttpURLConnection) redirect.openConnection(); // 打开URL连接
            redirectConnection.setRequestMethod("GET"); // 设置请求方法为GET
            redirectConnection.getResponseCode(); // 获取响应码
        } else {
            throw new IOException("Failed to redirect to login page."); // 如果没有重定向地址，则抛出IOException异常
        }
    }

    /**
     * 读取HTTP连接的响应内容并返回字符串形式的响应结果
     *
     * @param connection HTTP连接对象
     * @return 响应结果的字符串形式
     * @throws IOException 如果读取连接流发生异常
     */
    public static String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // 创建BufferedReader对象读取连接的输入流
        StringBuilder response = new StringBuilder(); // 创建StringBuilder对象用于拼接响应结果
        String line; // 用于存储每行读取的内容
        while ((line = reader.readLine()) != null) { // 循环读取每行内容直到读取到null
            response.append(line); // 将每行内容添加到响应结果中
        }
        reader.close(); // 关闭BufferedReader对象
        return response.toString(); // 将响应结果转换为字符串并返回
    }

}
