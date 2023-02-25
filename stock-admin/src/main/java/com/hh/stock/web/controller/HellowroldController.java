package com.hh.stock.web.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : hh
 * @date : 2023/2/9 17:39
 * @description : some description
 */

@RestController
@EnableAutoConfiguration
public class HellowroldController {
    @RequestMapping("/hello")
    @ResponseBody
    public Object hello(){
        return "hello";
    }

}
