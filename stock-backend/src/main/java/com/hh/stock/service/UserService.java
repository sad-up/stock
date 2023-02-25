package com.hh.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.stock.domain.sys.User;
import com.hh.stock.vo.req.LoginReqVo;
import com.hh.stock.vo.resp.LoginRespVo;
import com.hh.stock.vo.resp.R;

import java.util.Map;

/**
 * @author : hh
 * @date : 2023/1/27 13:24
 * @description : 定义用户接口
 */

public interface UserService extends IService<User> {

    /**
     * 用户登录功能
     * @param vo
     * @return
     */
    R<LoginRespVo> login(LoginReqVo vo);

    /**
     * 生成登录验证码
     * @return
     */
    R<Map> genCaptchaImage();

    /**
     * 根据用户名查询用户
     *
     * */

    User findUserByUserName(String username);
}
