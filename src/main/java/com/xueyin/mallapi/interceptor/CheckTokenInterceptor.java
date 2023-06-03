package com.xueyin.mallapi.interceptor;

import com.xueyin.mallapi.common.Constants;
import com.xueyin.mallapi.exception.BusinessException;
import com.xueyin.mallapi.exception.TokenException;
import com.xueyin.mallapi.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Autowired
    public RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws TokenException {
        //检查用户是否携带token     表示是否登录
        String token = request.getHeader("Authorization");
        if (token == null){
            throw new TokenException("检测到未登录，请登录");
        }

        //检测当前登录是否失效:Redis中是否有token的key
        if (!redisUtils.exists(Constants.LOGIN_ADMIN_INFO_TOKEN + token)) {
            throw new TokenException("登录失效，请重新登录");
        }

        //放行
        return true;
    }
}
