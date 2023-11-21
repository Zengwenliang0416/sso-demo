package com.apusic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月20日 10:32:53
 * @packageName com.apusic.controller
 * @className Index
 * @describe TODO
 */
@Controller
public class Index {
    @GetMapping("/getIndex")
    public String getIndex() {
        return "cloudship-index";
    }
}
