package com.xueyin.mallapi.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyin.mallapi.common.Constants;
import com.xueyin.mallapi.entity.Admin;
import com.xueyin.mallapi.exception.BusinessException;
import com.xueyin.mallapi.mapper.AdminMapper;
import com.xueyin.mallapi.qo.LoginQo;
import com.xueyin.mallapi.service.LoginService;
import com.xueyin.mallapi.utils.RedisUtils;
import com.xueyin.mallapi.utils.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Map<String, String> getCaptcha() {
        //获取验证码
        Map<String, String> map = VerifyCodeUtil.generateVerifyCode();
        //从map获取验证码
        String code = map.get("code");
        String uuid = map.get("uuid");
        // 将验证码存储到Redis中，并设置过期时间
        redisUtils.set(Constants.LOGIN_CAPTCHA_CODE + uuid,code,Constants.LOGIN_CAPTCHA_CODE_EXPIRE_TIME);

        //将map存储的验证码删除
        map.remove("code");

        return map;
    }

    @Override
    public Map<String, String> login(LoginQo qo) {
        //校验验证码是否正确
        //1.从Redis中获取验证码
        String code = redisUtils.get(Constants.LOGIN_CAPTCHA_CODE + qo.getUuid());
        //2.判断用户输入的验证码与Redis中的验证码是否一致
        //如果验证码不一致
        if (!VerifyCodeUtil.verification(code,qo.getCode(),true)) {
            //抛出一个异常
            throw new BusinessException("验证码错误");
        }
        //验证码一致
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getAdminName,qo.getUsername()).eq(Admin::getAdminPassword,qo.getPassword());
        Admin loginAdmin = adminMapper.selectOne(wrapper);

        //如果loginAdmin为null，说明登录失败
        if (loginAdmin == null) {
            throw new BusinessException("账号或密码输入错误");
        }

        //登录成功并生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        //将登录的用户信息存储到Redis中，token作为key，登录的用户对象作为value
        redisUtils.set(Constants.LOGIN_ADMIN_INFO_TOKEN + token, JSONUtil.toJsonStr(loginAdmin),Constants.LOGIN_ADMIN_INFO_TOKEN_EXPIRE_TIME);

        Map<String,String> map = new HashMap<>();
        map.put("token",token);

        return map;
    }

    @Override
    public Admin getUserInfo(HttpServletRequest request) {
        //获取用户信息逻辑
        //1.获取请求头的token
        String token = request.getHeader("Authorization");
        //2.从Redis中获取用户信息
        String loginAdminJson = redisUtils.get(Constants.LOGIN_ADMIN_INFO_TOKEN + token);
        //3.将LoginAdminJson转成admin对象
        Admin loginAdmin = JSONUtil.toBean(loginAdminJson, Admin.class);

        //将loginAdmin中密码清空
        loginAdmin.setAdminPassword(null);

        return loginAdmin;
    }

    @Override
    public void logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        //将Redis中存储的token删除
        redisUtils.del(Constants.LOGIN_ADMIN_INFO_TOKEN + token);
    }
}
