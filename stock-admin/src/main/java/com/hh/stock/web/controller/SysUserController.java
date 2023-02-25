package com.hh.stock.web.controller;



import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author : hh
 * @date : 2023/1/26 13:13
 * @description : some description
 */

@RestController
@RequestMapping("/api")
public class SysUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @ResponseBody
    public List<User> list(User user){
       List<User> list = userService.selectUsersList(user);
       return list;
   }
}
