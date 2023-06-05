package com.xueyin.mallapi.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyin.mallapi.common.Result;
import com.xueyin.mallapi.entity.User;
import com.xueyin.mallapi.qo.UserQo;
import com.xueyin.mallapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xueyin
 * @since 2023-06-05
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    //用户列表
    @GetMapping("list")
    public Result list(UserQo qo){
        IPage<User> page = userService.page(qo);
        return Result.success("查询成功",page);
    }

}

