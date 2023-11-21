import com.alibaba.fastjson.JSONObject;
import com.apusic.service.SSO;
import org.junit.Test;

import javax.security.auth.message.MessageInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月21日 09:43:58
 * @packageName PACKAGE_NAME
 * @className SSOTest
 * @describe TODO
 */
public class SSOTest {
    private SSO sso;


    @Test
    public void testValidateRequest() throws IOException {
        HttpServletRequest request = null;
        List<String> cookies = new ArrayList<>();
        cookies.add("token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE2MDU0NzU3MzYsImV4cCI6MTYwNTQ3NTczNn0.02_27_10_10_10_10_10_10_10_1");
        cookies.add("damin=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE2MDU0NzU3MzYsImV4cCI6MTYwNTQ3NTczNn0.02_27_10_10_10_10_10_10_10_1");
        cookies.add("root=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE2MDU0NzU3MzYsImV4cCI6MTYwNTQ3NTczNn0.02_27_10_10_10_10_10_10_10_1");
        request.setAttribute("Cookie", cookies);
    }

    @Test
    public void testValidateRequest_validCookie() throws IOException {
        List<String> cookies = new ArrayList<>();
        cookies.add("token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE2MDU0NzU3MzYsImV4cCI6MTYwNTQ3NTczNn0.02_27_10_10_10_10_10_10_10_1");
        cookies.add("damin=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE2MDU0NzU3MzYsImV4cCI6MTYwNTQ3NTczNn0.02_27_10_10_10_10_10_10_10_1");
        cookies.add("root=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE2MDU0NzU3MzYsImV4cCI6MTYwNTQ3NTczNn0.02_27_10_10_10_10_10_10_10_1");
        URL url = new URL("http://localhost:8080/v1/validation"); // 填入验证接口的URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
//        for (String cookie : cookies) {
//            connection.setRequestProperty("Cookie", cookie);
//        }
        connection.addRequestProperty("Cookie", "token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE2MDU0NzU3MzYsImV4cCI6MTYwNTQ3NTczNn0.02_27_10_10_10_10_10_10_10_1");
        connection.addRequestProperty("Cookie", "admin=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE2MDU0NzU3MzYsImV4cCI6MTYwNTQ3NTczNn0.02_27_10_10_10_10_10_10_10_1");
        connection.addRequestProperty("Cookie", "root=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE2MDU0NzU3MzYsImV4cCI6MTYwNTQ3NTczNn0.02_27_10_10_10_10_10_10_10_1");

        int responseCode = connection.getResponseCode();
        JSONObject loginInfo = JSONObject.parseObject(sso.readResponse(connection))
                .getJSONObject("result")
                .getJSONObject("loginInfo");
        if (loginInfo.getInteger("type") == 0) {
            String loginUrl = loginInfo.getString("loginUrl");
        }
        int a = responseCode;
    }

}
