package com.hh.stock.system.service.impl;

import com.hh.stock.common.config.StockConfig;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.stock.InnerMarketDomain;
import com.hh.stock.common.utils.DateTimeUtil;
import com.hh.stock.system.domain.StockBusiness;
import com.hh.stock.system.mapper.StockBusinessMapper;
import com.hh.stock.system.mapper.StockMarketIndexInfoMapper;
import com.hh.stock.system.service.StockService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author : hh
 * @date : 2023/2/25 2:51
 * @description : some description
 */


@Service("stockService")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockConfig stockConfig;

    @Override
    public List<StockBusiness> findAll() {
        return stockBusinessMapper.findAll();
    }

    @Override
    public AjaxResult getNewAMarketInfo() {
        // 1. 获取国内A股大盘得id集合
        List<String> inners = stockConfig.getInner();
        // 2。 获取最近股票交易日期下对应的大盘信息
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        // 3. 将java中的Date
        Date lastDate = lastDateTime.toDate();
        //TDDD mock测试数据，后期数据通过第三方接口动态获取试试数据，可删除
        lastDate = DateTime.parse("2022-01-03 11:15:00", DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss")).toDate();
        // 4. 将获取的java Date传入接口
        List<InnerMarketDomain> list = stockMarketIndexInfoMapper.getMarkInfo(inners,lastDate);

        return AjaxResult.success(list);
    }
}




