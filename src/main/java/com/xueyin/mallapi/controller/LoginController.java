package com.xueyin.mallapi.controller;

import com.xueyin.mallapi.common.Result;
import com.xueyin.mallapi.entity.Admin;
import com.xueyin.mallapi.qo.LoginQo;
import com.xueyin.mallapi.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    //获取验证码
    @GetMapping("captcha")
    public Result getCaptcha(){
        //生成验证码
        Map<String,String> map = loginService.getCaptcha();
        return Result.success("验证码获取成功",map);
    }

    //登录
    @PostMapping("login")
    public Result login(@RequestBody LoginQo qo){
        Map<String, String> map = loginService.login(qo);

        return Result.success("登录成功",map);
    }

    //获得登录用户
    @GetMapping("userInfo")
    public Result getUserInfo(HttpServletRequest request){
        Admin admin = loginService.getUserInfo(request);
        return Result.success("获取用户信息成功",admin);
    }

    //退出登录
    @GetMapping("logout")
    public Result logout(HttpServletRequest request){
        loginService.logout(request);
        return Result.success("退出登录成功");
    }
}
