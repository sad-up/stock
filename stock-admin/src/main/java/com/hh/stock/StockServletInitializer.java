package com.hh.stock;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author : hh
 * @date : 2023/2/10 12:54
 * @description : web容器中进行部署
 */

public class StockServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(StockApplication.class);
    }

}
