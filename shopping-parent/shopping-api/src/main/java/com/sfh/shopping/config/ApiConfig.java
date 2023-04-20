package com.sfh.shopping.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        //在开启了mybatis的延迟加载之后，由于动态代理的原因，mybatis查询出的结果已经不是自定义的模型类，而是返回通过cglib生成的模型类的代理实例，这些代理实例在使用jackson序列化时，会出现以下异常：
        //com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.apache.ibatis.executor.loader.javassist.JavassistProxyFactory$EnhancedResultObjectProxyImpl
        //此处配置专用于解决这个问题
        return builder -> builder.failOnEmptyBeans(false);
    }
}
