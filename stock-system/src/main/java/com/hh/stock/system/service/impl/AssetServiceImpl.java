package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.Asset;
import com.hh.stock.common.utils.uuid.IdWorker;
import com.hh.stock.system.service.AssetService;
import com.hh.stock.system.mapper.AssetMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_asset】的数据库操作Service实现
* @createDate 2023-04-26 12:47:56
*/
@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset>
    implements AssetService{

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private AssetMapper assetMapper;

    /**
     *  通过用户账户新建账户
     * @param asset
     */
    @Override
    public void insertAccount(Asset asset) {
        asset.setId(idWorker.nextId()+"");
        assetMapper.insertUername(asset);
    }

    /**
     * 通过用户名获取用户账户信息
     * @return
     */
    @Override
    public AjaxResult getWallet(HttpServletRequest request) {
        String username = request.getParameter("username");
        List<Asset>  infos = assetMapper.getWalletUsername(username);
        return AjaxResult.success(infos);
    }
}




