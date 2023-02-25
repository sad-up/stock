package com.hh.stock.web.controller.stock;

import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.system.domain.StockBusiness;
import com.hh.stock.system.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
