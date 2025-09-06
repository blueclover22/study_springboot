package com.study.springboot.config;

import com.study.springboot.common.interceptor.AccessLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {

    private final AccessLoggingInterceptor accessLoggingInterceptor;

    @Override
    public void addInterceptors(
        @NonNull InterceptorRegistry registry) {

        log.debug("InterceptorConfig.addInterceptors");

        registry.addInterceptor(accessLoggingInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/resources/**")
            .excludePathPatterns("/users/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
