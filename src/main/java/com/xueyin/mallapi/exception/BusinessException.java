package com.xueyin.mallapi.exception;

/**
 * 自定义业务异常
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
