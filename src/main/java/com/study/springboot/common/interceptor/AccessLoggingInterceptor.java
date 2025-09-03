package com.study.springboot.common.interceptor;

import com.study.springboot.domain.AccessLog;
import com.study.springboot.service.AccessLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

@Slf4j
@Component
public class AccessLoggingInterceptor implements HandlerInterceptor {

    @Autowired
    private AccessLogService service;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String requestURI = request.getRequestURI();

        String remoteAddr = request.getRemoteAddr();

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            Method method = handlerMethod.getMethod();
            Class<?> clazz = method.getDeclaringClass();

            String className = clazz.getName();
            String classSimpleName = clazz.getSimpleName();
            String methodName = method.getName();

            log.info("requestURI={}, remoteAddr={}, className={}, , classSimpleName = {}, methodName={}", requestURI, remoteAddr, className, classSimpleName, methodName);

            AccessLog accessLog = new AccessLog();

            accessLog.setRequestUri(requestURI);
            accessLog.setClassName(className);
            accessLog.setClassSimpleName(classSimpleName);
            accessLog.setMethodName(methodName);
            accessLog.setRemoteAddr(remoteAddr);

            service.register(accessLog);
        } else {
            log.info("requestURI={}, remoteAddr={}", requestURI, remoteAddr);
        }

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }


}
