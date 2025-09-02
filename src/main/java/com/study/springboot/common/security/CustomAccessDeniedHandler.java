package com.study.springboot.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.springboot.common.exception.ApiErrorInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Locale;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private MessageSource messageSource;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        String message = messageSource.getMessage("common.accessDenied", null, Locale.KOREAN);

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        apiErrorInfo.setMessage(message);

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(apiErrorInfo);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(jsonString);

    }
}
