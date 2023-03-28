package com.hh.stock.web.controller.stock;

import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.stockvo.PageResult;
import com.hh.stock.common.core.domain.stockvo.StockUpdownDomain;
import com.hh.stock.system.domain.StockBusiness;
import com.hh.stock.system.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterGraphics;
import java.io.IOException;
import java.util.List;

/**
 * @author : hh
 * @date : 2023/2/25 2:35
 * @description : some description
 */
@RestController
@RequestMapping("/api/quot")
public class StockController {


    @Autowired
    private StockService stockService;

    @GetMapping("/stock/business/all")
    public List<StockBusiness> findAllStockBusinessInfo() {
        return stockService.findAll();
    }

    /**
     * 获取最新得A股大盘信息
     * 如果不在股票交易日则显示最近得交易数据信息
     * @return
     */
    @GetMapping("/index/all")
    public AjaxResult getNewAMarketInfo(){
        return stockService.getNewAMarketInfo();

    }

    /**
     *  沪深两市板块分时行情数据查询
     *  以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    @GetMapping("/sector/all")
    public AjaxResult sectorAll() {
        return stockService.sectorAllLimit();
    }

    /**
     * 统计沪深两市个股票最新交易数据，并按涨幅降序排序查询前10条数据
     * @return
     */
    @GetMapping("/stock/increase")
    public AjaxResult getStockRtInfoLimit(){
        return stockService.getStockRtInfoLimit();
    }

    /**
     * 分页查询股票交易数据，按照日期和涨幅降序排序
     * @param page 当前页
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("/stock/all")
    public AjaxResult getStockRtInfoPages(Integer page,Integer pageSize){
        return stockService.getStockRtInfoPages(page,pageSize);
    }

    /**
     * 统计T日（最近一次股票交易日）涨停和跌停得分时统计
     * @return
     */
    @GetMapping("/stock/updown/count")
    public AjaxResult getStockUpdownCount(){
        return stockService.getStockUpDownCount();
    }

    /**
     * 到处股票信息到excel下
     * @param response 响应对象，可获取流对象
     * @param page 当前页
     * @param pageSize 每页大小
     */
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) throws IOException {
        stockService.stockExport(response, page, pageSize);
    }

    /**
     * 统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     * 如果当前日期不在股票交易日，则按照前一个有效股票交易日作为T日查询
     * @return
     */
    @GetMapping("/stock/tradevol")
    public AjaxResult getStockTradeVolComparation(){
        return stockService.getStockTradeVolComparation();
    }

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
    @GetMapping("/stock/updown")
    public AjaxResult getStockUpDown(){
        return stockService.stockUpDownScopeCount();
    }

    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     *         如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     * @param stockCode 股票编码
     * @return
     */
    @GetMapping("/stock/screen/timesharing")
    public AjaxResult getStockScreenTimeSharing(@RequestParam("code") String stockCode){
        return stockService.stockScreenTimeSharing(stockCode);
    }


    /**
     * 单个个股日K 数据查询 ，可以根据时间区间查询数日的K线数据
     * @param code 股票编码
     */
    @RequestMapping("/stock/screen/dkline")
    public AjaxResult getDayKLinData(String code){
        return stockService.stockCreenDkLine(code);
    }
}
