package com.hh.stock.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : hh
 * @date : 2023/3/21 14:23
 * @description : some description
 */

public class TestEasyExcel {

    private List<User> users;

    @BeforeEach
    public void inirUser(){
        users = new ArrayList<>();
        for(int i = 0; i<10;i++){
            User user = User.builder().userName("zhang" + i).asddress("sh" + i).age(10 + i)
                    .birthDate(new Date()).build();
            users.add(user);
        }
    }

    @Test
    public void baseExport(){
        EasyExcel.write("C:\\Users\\Huanghe\\Desktop\\excel\\one.xls", User.class)
                .sheet("141")
                .doWrite(users);
    }
}
