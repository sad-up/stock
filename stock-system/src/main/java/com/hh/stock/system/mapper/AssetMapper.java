package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.entity.Asset;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_asset】的数据库操作Mapper
* @createDate 2023-04-26 12:47:56
* @Entity com.hh.stock.common.core.domain.entity.Asset
*/
public interface AssetMapper extends BaseMapper<Asset> {

    /**
     *  通过用户账户新建账户
     * @param asset
     */
    void insertUername(Asset asset);

    /**
     * 获取用户账户信息
     * @return
     */
    List<Asset> getWalletUsername(String username);

    /**
     * 更新金额
     * @param cash
     */
    void updateCash(@Param("buyerId") String buyerId,@Param("cash") BigDecimal cash);



    /**
     * 通过用户名查出buy_id
     * @param username
     */
    String findBuyIdByUsername(String username);

    /**
     * 通过用户名批量删除用户
     * @param username
     */
    void deleteAccount(@Param("usernames") List username);

    /**
     * 更新股票
     * @param asset
     */
    void updateStock(@Param("asset") Asset asset,@Param("buy") String buy);

    /**
     * 通过用户名查找账户金额
     * @param username
     * @return
     */
    BigDecimal findCashByUsername(String username);

    /**
     * 通过用户名查找股票
     * @param username
     * @return
     */
    BigDecimal findStockByUsername(String username);
}




