package com.hh.stock.web.controller.common;

import com.google.code.kaptcha.Producer;
import com.hh.stock.common.config.StockConfig;
import com.hh.stock.common.constant.CacheConstants;
import com.hh.stock.common.constant.Constants;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.redis.RedisCache;
import com.hh.stock.common.utils.sign.Base64;
import com.hh.stock.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author : hh
 * @date : 2023/2/10 13:10
 * @description : 验证码操作处理
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class CaptchaController {

    @Autowired
    private RedisCache redisCache;
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;
    @GetMapping("/captchaImage")
    public AjaxResult getCode(){
        AjaxResult ajax = AjaxResult.success();
        boolean captchaOnOff = true;
        if(!captchaOnOff){
            return ajax;
        }
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;


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
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

}
