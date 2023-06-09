package com.hh.stock;


import com.alipay.api.AlipayConfig;
import com.hh.stock.common.config.vo.StockConfig;
import com.hh.stock.common.config.vo.TaskThreadPoolInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan(basePackages = {"com.hh.stock.web.**"})
@ComponentScan("com.hh.**.service")
@MapperScan("com.hh.**.mapper")
@EnableConfigurationProperties({StockConfig.class, TaskThreadPoolInfo.class}) //开启配置初始化,加入IOC容器
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

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(180000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return factory;
    }
}