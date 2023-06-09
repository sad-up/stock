package com.hh.stock.system.service;

import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.StockOrders;
import com.hh.stock.system.domain.StockBusiness;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author : hh
 * @date : 2023/2/25 2:44
 * @description : 股票相关复习接口
 */

public interface StockService {

    /**
     *  查询所有得直营业务项
     * @return
     */
    List<StockBusiness> findAll();

    /**
     * 获取最新得A股大盘信息
     * 如果不在股票交易日则显示最近得交易数据信息
     * @return
     */
    AjaxResult getNewAMarketInfo();

    /**
     * 沪深两市板块分时行情数据查询
     * 以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    AjaxResult sectorAllLimit();

    /**
     * 统计沪深两市个股票最新交易数据，并按涨幅降序排序查询前10条数据
     * @return
     */
    AjaxResult getStockRtInfoLimit();

    /**
     * 分页查询股票交易数据，按照日期和涨幅降序排序
     * @param page 当前页
     * @param pageSize 每页大小
     * @return
     */
    AjaxResult getStockRtInfoPages(Integer page, Integer pageSize);

    /**
     * 统计T日（最近一次股票交易日）涨停和跌停得分时统计
     * @return
     */
    AjaxResult getStockUpDownCount();

    /**
     * 到处股票信息到excel下
     * @param response 响应对象，可获取流对象
     * @param page 当前页
     * @param pageSize 每页大小
     */
    void stockExport(HttpServletResponse response, Integer page, Integer pageSize) throws IOException;

    /**
     * 统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     * 如果当前日期不在股票交易日，则按照前一个有效股票交易日作为T日查询
     * @return
     */
    AjaxResult getStockTradeVolComparation();

    /**
     * 查询当前时间下股票的涨跌幅度区间统计功能
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点
     * @return
     *   响应数据格式：
     *  {
     *     "code": 1,
     *     "data": {
     *         "time": "2021-12-31 14:58:00",
     *         "infos": [
     *             {
     *                 "count": 17,
     *                 "title": "-3~0%"
     *             },
     *             //...
     *             ]
     *     }
     */
    AjaxResult stockUpDownScopeCount();

    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     *         如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     * @param stockcode 股票编码
     * @return
     */
    AjaxResult stockScreenTimeSharing(String stockcode);

    /**
     * 单个个股日K 数据查询 ，可以根据时间区间查询数日的K线数据
     * @param code 股票编码
     */
    AjaxResult stockCreenDkLine(String code);

    /**
     * 获取最新外盘信息
     * @return
     */
    AjaxResult getExternalAMarketInfo();

    /**
     * 获取个股主营业务
     * @param code 股票id
     * @return
     */
    AjaxResult getStockDescribe(String code);

    /**
     * 获取个股最新分时行情数据，主要包含：
     * 	开盘价、前收盘价、最新价、最高价、最低价、成交金额和成交量、交易时间信息;
     * @param code 股票编码
     * @return
     */
    AjaxResult getStockStockDetail(String code);

    /**
     * 功能描述：根据输入的个股代码，进行模糊查询，返回证券代码和证券名称
     * @param code
     * @return
     */
    AjaxResult getStockSearch(String code);

    /**
     * 功能描述：个股交易流水行情数据查询--查询最新交易流水，按照交易时间降序取前10
     * @param code
     * @return
     */
    AjaxResult getStockScreenSecond(String code);

    /**
     * 功能描述：统计每周内的股票数据信息，信息包含：
     * 	股票ID、 一周内最高价、 一周内最低价 、周1开盘价、周5的收盘价、
     * 	整周均价、以及一周内最大交易日期（一般是周五所对应日期）;
     * @param code
     * @return
     */
    AjaxResult getStockWeekKline(String code);

    /**
     * 获取个股股票信息
     * @return
     */
    AjaxResult getStockInfo(String code);

    /**
     * 购买股票
     */
    AjaxResult stockBuy(StockOrders stockOrders);
}
