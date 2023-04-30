package com.hh.stock.system.service;

import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.Asset;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_asset】的数据库操作Service
* @createDate 2023-04-26 12:47:56
*/
public interface AssetService extends IService<Asset> {

    /**
     *  通过用户账户新建账户
     * @param asset
     */
    void insertAccount(Asset asset);

    /**
     * 通过用户名获取用户账户信息
     * @return
     */
    AjaxResult getWallet(HttpServletRequest request);

    /**
     * 通过用户名删除用户
     * @param username
     */
    void deleteAccount(List username);
}
