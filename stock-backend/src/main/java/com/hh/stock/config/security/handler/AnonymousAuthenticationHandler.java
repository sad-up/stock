package com.hh.stock.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.hh.stock.vo.resp.R;
import com.hh.stock.vo.resp.ResponseCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author : hh
 * @date : 2023/1/22 22:56
 * @description : 匿名用户访问资源处理器
 */

@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {



    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //设置客户端的响应的内容类型
        response.setContentType("application/json;charset=UTF-8");
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //消除循环引用
        String result = JSON.toJSONString(R.error(ResponseCode.NO_LOGIN.getMessage()), SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
