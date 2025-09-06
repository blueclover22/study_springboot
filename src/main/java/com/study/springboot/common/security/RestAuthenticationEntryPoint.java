package com.study.springboot.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.springboot.common.exception.ApiErrorInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.debug("RestAuthenticationEntryPoint.commence");

        response.setContentType("application/json;charset=UTF-8");

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        if(InsufficientAuthenticationException.class.isAssignableFrom(authException.getClass())){
            apiErrorInfo.setMessage("Not Login!");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

        }else{
            apiErrorInfo.setMessage("Bad Request!");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        String jsonString = objectMapper.writeValueAsString(apiErrorInfo);
        response.getWriter().write(jsonString);
    }
}
