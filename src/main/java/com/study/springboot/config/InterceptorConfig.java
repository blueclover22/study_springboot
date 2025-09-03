package com.study.springboot.config;

import com.study.springboot.common.interceptor.AccessLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final AccessLoggingInterceptor accessLoggingInterceptor;

    @Override
    public void addInterceptors(
        @NonNull InterceptorRegistry registry) {

        registry.addInterceptor(accessLoggingInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/resources/**")
            .excludePathPatterns("/users/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
