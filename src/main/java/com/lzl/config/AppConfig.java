package com.lzl.config;

import com.lzl.interceptor.FrontHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lizanle
 * @data 2019/3/18 9:02 PM
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor());
    }

    @Bean
    public HandlerInterceptor handlerInterceptor(){
        return new FrontHandlerInterceptor();
    }
}
