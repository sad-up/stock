package com.hh.stock;


import com.hh.stock.common.config.StockConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hh.stock.web.**"})
@ComponentScan("com.hh.**.service")
@MapperScan("com.hh.**.mapper")
@EnableConfigurationProperties(StockConfig.class) //开启配置初始化,加入IOC容器
public class StockApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  stock启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .———.    .———.   .———.    .———.    \n" +
                " |   |    |   |   |   |    |   |    \n" +
                " |   |    |   |   |   |    |   |    \n" +
                "    ᕬ ᕬ                ᘏ▸◂ᘏ        \n" +
                "  ( ˶•ꈊ•） ｡ﾟﾟ･｡･ﾟﾟ｡  （ ˃̵ ֊ ˂̵ ）    \n" +
                " ⁽⁽ଘ/͡ つ~☆  ﾟ。｡ ﾟ  (つ❤ど)  ⸝⸝      \n" +
                " |   |    |   |   |   |    |   |    \n" +
                " |   |    |   |   |   |    |   |    \n" +
                " '———'    '———'   '———'    '———'     ");
    }
}