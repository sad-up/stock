package com.hh.stock.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.google.common.base.Strings;
import com.hh.stock.common.constant.CacheConstants;
import com.hh.stock.common.constant.Constants;
import com.hh.stock.common.core.redis.RedisCache;
import com.hh.stock.config.captcha.StockConfig;
import com.hh.stock.domain.sys.User;
import com.hh.stock.utils.sign.Base64;
import com.hh.stock.utils.uuid.IdUtils;
import com.hh.stock.vo.req.LoginReqVo;
import com.hh.stock.vo.resp.LoginRespVo;
import com.hh.stock.mapper.sys.UserMapper;
import com.hh.stock.service.UserService;
import com.hh.stock.vo.resp.R;
import com.hh.stock.vo.resp.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.TimeUnit;


/**
 * @author : hh
 * @date : 2023/1/27 13:26
 * @description : 针对表【sys_user】的数据库操作Service实现
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        // 1.判断vo是否存在 或者用户名是否存在 或者 密码是否存在 或者校验码 或者sessionid
        if (vo == null || Strings.isNullOrEmpty(vo.getUsername()) || Strings.isNullOrEmpty(vo.getPassword())
                || Strings.isNullOrEmpty(vo.getCode()) || Strings.isNullOrEmpty(vo.getUuid())) {
            // 快速淘汰校验码，合理利用redis的内存空间
            redisTemplate.delete(vo.getUuid());
            return R.error(ResponseCode.DATA_ERROR.getMessage());

        }
        // 校验验证码
        // 获取redis中rkey对应的验证码
        String redisCode = (String) redisTemplate.opsForValue().get(vo.getUuid());
        // 比对
        if (redisCode == null && !redisCode.equals(vo.getCode())) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        // 快速淘汰校验码，合理利用redis的内存空间
        redisTemplate.delete(vo.getUuid());
        // 2.根据用户名查询用户是否存在
        User userInfo = userMapper.findUserInfoByUserName(vo.getUsername());
        if(userInfo == null){
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        // 3.判断密码是否一致
        if (!passwordEncoder.matches(vo.getPassword(),userInfo.getPassword())) {
            return R.error(ResponseCode.SYSTEM_PASSWORD_EXPIRED.getMessage());
        }
        // 4.属性赋值 两个类之间属性名称一致
        LoginRespVo respVo = new LoginRespVo();
        BeanUtils.copyProperties(userInfo, respVo);

        return R.ok(respVo);
    }


    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;
    /**
     * 生成登录验证码
     * @return
     */
    @Override
    public R<Map> genCaptchaImage() {
//        // 1.生成随机验证码的长度
//        String checkCode = RandomStringUtils.randomNumeric(4);
//
//        // 2.生成一个类似sessionid的id作为key，然后验证码作为value保存redis下，同时设置有效期3分钟
//
//        String sessionId = String.valueOf(idWorker.nextId());
//        redisTemplate.opsForValue().set(sessionId, checkCode, 3 , TimeUnit.MINUTES);
//        // 3.组装相应的map对象
//        HashMap<String, String> map = new HashMap<>();
//        map.put("code", checkCode);
//        map.put("rkey", sessionId);
//        // 4.返回组装数据
//
//        return R.ok(map);
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;


        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = StockConfig.getCaptchaType();
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return R.error(e.getMessage());
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("img",  Base64.encode(os.toByteArray()));
        return R.ok(map);
    }

    /**
     * 根据用户名查询用户
     *
     * */
    @Override
    public User findUserByUserName(String username) {
        //创建条件构造器对象
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        //执行查询
        return baseMapper.selectOne(queryWrapper);
    }



}
