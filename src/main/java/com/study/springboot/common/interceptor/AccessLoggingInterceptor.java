package com.study.springboot.common.interceptor;

import com.study.springboot.domain.AccessLog;
import com.study.springboot.service.AccessLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessLoggingInterceptor implements HandlerInterceptor {

    private final AccessLogService service;

    @Override
    public void postHandle(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull Object handler, 
        @Nullable ModelAndView modelAndView) throws Exception {

        log.debug("AccessLoggingInterceptor.postHandle");

        String requestUri = request.getRequestURI();

        String remoteAddr = request.getRemoteAddr();

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            Method method = handlerMethod.getMethod();
            Class<?> clazz = method.getDeclaringClass();

            String className = clazz.getName();
            String classSimpleName = clazz.getSimpleName();
            String methodName = method.getName();

            log.info("requestURI={}, remoteAddr={}, className={}, classSimpleName={}, methodName={}", requestUri, remoteAddr, className, classSimpleName, methodName);

            AccessLog accessLog = new AccessLog();

            accessLog.setRequestUri(requestUri);
            accessLog.setClassName(className);
            accessLog.setClassSimpleName(classSimpleName);
            accessLog.setMethodName(methodName);
            accessLog.setRemoteAddr(remoteAddr);

            try {
                service.register(accessLog);
            } catch (Exception e) {
                log.error("Failed to save access log: {}", e.getMessage(), e);
            }
        } else {
            log.info("requestURI={}, remoteAddr={}", requestUri, remoteAddr);
        }

    }

    @Override
    public boolean preHandle(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull Object handler) throws Exception {

        log.debug("AccessLoggingInterceptor.preHandle");

        return true;
    }


}
