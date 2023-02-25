package com.hh.stock.controller;

import com.hh.stock.service.UserService;
import com.hh.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : hh
 * @date : 2023/1/27 0:56
 * @description : some description
 */

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 生成登录验证码
     * @return
     */
    @GetMapping("/captchaImage")
    public R<Map> genCaptchaImage() {
        return userService.genCaptchaImage();
    }


}