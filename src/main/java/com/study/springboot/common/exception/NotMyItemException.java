package com.study.springboot.common.exception;

public class NotMyItemException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NotMyItemException(){
    }

    public NotMyItemException(String message){
        super(message);
    }
}
