package com.study.springboot.common.exception;

public class NotEnoughCoinException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NotEnoughCoinException(){
    }

    public NotEnoughCoinException(String message){
        super(message);
    }
}
