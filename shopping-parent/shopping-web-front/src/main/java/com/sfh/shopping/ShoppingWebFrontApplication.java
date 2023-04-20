package com.sfh.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class ShoppingWebFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingWebFrontApplication.class, args);
    }

}
