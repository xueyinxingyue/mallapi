package com.xueyin.mallapi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyin.mallapi.common.Result;
import com.xueyin.mallapi.entity.Property;
import com.xueyin.mallapi.service.IPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 类别属性表 前端控制器
 * </p>
 *
 * @author xueyin
 * @since 2023-06-01
 */
@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    private IPropertyService propertyService;

    //根据分类id查询属性信息
    @GetMapping("list/{categoryId}")
    public Result list(@PathVariable Integer categoryId){
        LambdaQueryWrapper<Property> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Property::getPropertyCategoryId,categoryId);

        List<Property> propertyList = propertyService.list(wrapper);
        return Result.success("属性信息查询成功",propertyList);
    }
}

