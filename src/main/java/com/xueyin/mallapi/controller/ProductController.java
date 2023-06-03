package com.xueyin.mallapi.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyin.mallapi.common.Result;
import com.xueyin.mallapi.entity.Product;
import com.xueyin.mallapi.qo.ProductQo;
import com.xueyin.mallapi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 产品表 前端控制器
 * </p>
 *
 * @author xueyin
 * @since 2023-06-03
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    //商品列表
    @GetMapping("list")
    public Result list(ProductQo qo){
        IPage<Product> page = productService.page(qo);
        return Result.success("查询成功",page);
    }

    //图片上传
    @PostMapping("upload")
    public Result upload(MultipartFile file,String imageType){
        String uploadPath = productService.upload(file, imageType);
        return Result.success("图片上传成功",uploadPath);
    }

    //商品添加
    @PostMapping("insert")
    public Result insert(@RequestBody Product product){
        productService.save(product);
        return Result.success("商品添加成功");
    }
}

