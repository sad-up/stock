package com.hh.stock.web.controller.system;

import com.hh.stock.common.core.controller.BaseController;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.system.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : hh
 * @date : 2023/4/26 13:17
 * @description : 账户信息接口
 */
@RestController
@RequestMapping("/api/account")
public class SysAssetController extends BaseController {

    @Autowired
    private AssetService assetService;

    /**
     * 通过用户名获取用户账户信息
     * @return
     */
    @PostMapping("/wallet")
    public AjaxResult getWallet(HttpServletRequest request){
        return assetService.getWallet(request);
    }
}
