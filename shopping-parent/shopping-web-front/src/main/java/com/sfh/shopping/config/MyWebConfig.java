package com.sfh.shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    /*配置拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginHandlerInterceptor())
                //配置要拦截的地址，黑名单
                .addPathPatterns("/cart/**/*","/order/**/*","/personal/**/*");

                //配置不进行拦截的地址，白名单
                //.excludePathPatterns("/user/**/*");
    }
}
