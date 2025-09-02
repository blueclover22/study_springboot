package com.study.springboot.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Locale;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        apiErrorInfo.setMessage(ex.getMessage());

        return super.handleExceptionInternal(ex, apiErrorInfo, headers, statusCode, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNotMyItemException(NotMyItemException ex, WebRequest request) {
        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();

        String message = messageSource.getMessage("item.notMyItem", null, Locale.KOREAN);
        apiErrorInfo.setMessage(message);

        return super.handleExceptionInternal(ex, apiErrorInfo, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNotEnoughCoinException(NotEnoughCoinException ex, WebRequest request) {
        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();

        String message = messageSource.getMessage("coin.notEnoughCoin", null, Locale.KOREAN);
        apiErrorInfo.setMessage(message);

        return super.handleExceptionInternal(ex, apiErrorInfo, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();

        String message = messageSource.getMessage("common.accessDenied", null, Locale.KOREAN);
        apiErrorInfo.setMessage(message);

        return super.handleExceptionInternal(ex, apiErrorInfo, null, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleSystemException(Exception ex, WebRequest request) {
        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        apiErrorInfo.setMessage(ex.getMessage());

        return super.handleExceptionInternal(ex, apiErrorInfo, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();

        StringBuffer sbMessage = new StringBuffer();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();

            sbMessage.append(field).append(" : ").append(message).append("\r\n");
        }

        apiErrorInfo.setMessage(sbMessage.toString());

        return super.handleExceptionInternal(ex, apiErrorInfo, headers, status, request);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Object> handleBindExceptionCustom(BindException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

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

        return super.handleExceptionInternal(ex, apiErrorInfo,headers, status, request);
    }
}
