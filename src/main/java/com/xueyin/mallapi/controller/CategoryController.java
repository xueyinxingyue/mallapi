package com.xueyin.mallapi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyin.mallapi.common.Result;
import com.xueyin.mallapi.entity.Category;
import com.xueyin.mallapi.qo.CategoryQo;
import com.xueyin.mallapi.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类别表 前端控制器
 * </p>
 *
 * @author xueyin
 * @since 2023-06-01
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    //分类信息列表请求
    @GetMapping("list")
    public Result list(CategoryQo qo){
        IPage<Category> page = categoryService.page(qo);
        return Result.success("分类信息查询成功",page);
    }

    //分类添加
    @PostMapping("insert")
    public Result insert(@RequestBody Map<String,Object> map){
        categoryService.save(map);
        return Result.success("分类信息添加成功");
    }

    //根据ID查询分类信息
    @GetMapping("info/{id}")
    public Result getInfo(@PathVariable Integer id){
        Category category = categoryService.getById(id);
        return Result.success("分类信息查询成功",category);
    }

    //修改
    @PutMapping("update")
    public Result update(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.success("分类信息修改成功");
    }

    //删除
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Integer id){
        categoryService.removeById(id);
        return Result.success("分类信息删除成功");
    }

    //查询所有
    @GetMapping("listAll")
    public Result listAll(){
        List<Category> categoryList = categoryService.list();
        return Result.success("所有分类信息查询成功",categoryList);
    }
}

