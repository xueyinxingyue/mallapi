package com.xueyin.mallapi.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;

    //请求成功
    public static Result success(int code,String message,Object data){
        return new Result(code,message,data);
    }
    public static Result success(String message,Object data){
        return new Result(200,message,data);
    }
    public static Result success(String message){
        return new Result(200,message,null);
    }
    public static Result success(Object data){
        return new Result(200,null,data);
    }

    //请求失败
    public static Result fail(int code,String message){
        return new Result(code,message,null);
    }

    public static Result fail(){
        return new Result(500,"服务器异常",null);
    }
}
