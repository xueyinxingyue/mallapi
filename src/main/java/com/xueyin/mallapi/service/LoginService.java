package com.xueyin.mallapi.service;

import com.xueyin.mallapi.entity.Admin;
import com.xueyin.mallapi.qo.LoginQo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService {
    //获取验证码
    public Map<String,String> getCaptcha();

    //登录
    public Map<String,String> login(LoginQo qo);

    Admin getUserInfo(HttpServletRequest request);
}
