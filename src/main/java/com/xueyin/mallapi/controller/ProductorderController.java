package com.xueyin.mallapi.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyin.mallapi.common.Result;
import com.xueyin.mallapi.entity.Productorder;
import com.xueyin.mallapi.qo.ProductOrderQo;
import com.xueyin.mallapi.service.IProductorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 产品订单表 前端控制器
 * </p>
 *
 * @author xueyin
 * @since 2023-06-07
 */
@RestController
@RequestMapping("/productorder")
public class ProductorderController {
    @Autowired
    private IProductorderService productorderService;

    //订单列表
    @GetMapping("list")
    public Result list(ProductOrderQo qo){
        IPage<Productorder> page = productorderService.page(qo);
        return Result.success("订单信息获取成功",page);
    }
}

