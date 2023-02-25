package com.hh.stock.controller;

import com.hh.stock.domain.StockBusiness;
import com.hh.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : hh
 * @date : 2023/1/27 10:04
 * @description : some description
 */

@RestController
@RequestMapping("/stockapi/quot")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stock/business/all")
    public List<StockBusiness> findAllStockBusinessInfo() {
        return stockService.findAll();
    }
}
