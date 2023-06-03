package com.xueyin.mallapi.advice;

import com.xueyin.mallapi.common.Result;
import com.xueyin.mallapi.exception.BusinessException;
import com.xueyin.mallapi.exception.TokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public Result handler(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    //处理自己的异常
    @ExceptionHandler(BusinessException.class)
    public Result handler(BusinessException e){
        e.printStackTrace();
        return  Result.fail(501, e.getMessage());
    }

    @ExceptionHandler(TokenException.class)
    public Result handler(TokenException e){
        e.printStackTrace();
        return  Result.fail(401, e.getMessage());
    }
}
