package com.study.springboot.common.exception;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(NotMyItemException.class)
    public ResponseEntity<Object> handleNotMyItemException(NotMyItemException ex, WebRequest request) {
        log.warn("NotMyItemException occurred: {}", ex.getMessage());

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();

        String message = messageSource.getMessage("item.notMyItem", null, Locale.KOREAN);
        apiErrorInfo.setMessage(message);

        return handleExceptionInternal(ex, apiErrorInfo, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNotEnoughCoinException(NotEnoughCoinException ex, WebRequest request) {
        log.warn("NotEnoughCoinException occurred: {}", ex.getMessage());

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();

        String message = messageSource.getMessage("coin.notEnoughCoin", null, Locale.KOREAN);
        apiErrorInfo.setMessage(message);

        return handleExceptionInternal(ex, apiErrorInfo, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.warn("AccessDeniedException occurred: {}", ex.getMessage());

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();

        String message = messageSource.getMessage("common.accessDenied", null, Locale.KOREAN);
        apiErrorInfo.setMessage(message);

        return handleExceptionInternal(ex, apiErrorInfo, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleSystemException(Exception ex, WebRequest request) {
        log.error("System exception occurred: {}", ex.getMessage(), ex);

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        apiErrorInfo.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, apiErrorInfo, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            WebRequest request) {

        log.warn("Validation failed for request: {}", ex.getMessage());

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();

        StringBuffer sbMessage = new StringBuffer();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();

            sbMessage.append(field).append(" : ").append(message).append("\r\n");
        }

        apiErrorInfo.setMessage(sbMessage.toString());

        return handleExceptionInternal(ex, apiErrorInfo, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex, WebRequest request) {

        log.warn("Bind exception occurred: {}", ex.getMessage());

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        apiErrorInfo.setMessage(ex.getMessage());

        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();

        for (ObjectError objectError : globalErrors) {
            apiErrorInfo.addDetailInfo(objectError.getObjectName(), objectError.getDefaultMessage());
        }

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            apiErrorInfo.addDetailInfo(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return handleExceptionInternal(ex, apiErrorInfo, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error("Exception occurred during request processing: {}", ex.getMessage(), ex);

        if (body == null) {
            ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
            apiErrorInfo.setMessage(ex.getMessage());
            body = apiErrorInfo;
        }

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
        // 정적 리소스 요청은 DEBUG 레벨로만 로깅 (에러가 아님)
        log.debug("Static resource not found: {}", ex.getMessage());

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
