package org.iimsa.product_service;

import org.iimsa.common.exception.GlobalExceptionAdvice;
import org.iimsa.config.security.LoginFilter;
import org.iimsa.config.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
@Import({SecurityConfig.class, LoginFilter.class, GlobalExceptionAdvice.class})
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
