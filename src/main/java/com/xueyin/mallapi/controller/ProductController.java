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

    //根据id获取商品信息
    @GetMapping("info/{id}")
    public Result getInfo(@PathVariable Integer id){
        Product product = productService.getById(id);
        return Result.success("商品信息获取成功",product);
    }

    //修改商品信息
    @PutMapping("update")
    public Result update(@RequestBody Product product){
        productService.updateById(product);
        return Result.success("商品信息修改成功");
    }

    //删除商品信息
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Integer id){
        productService.removeById(id);
        return Result.success("商品信息删除成功");
    }
}

