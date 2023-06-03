package com.xueyin.mallapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyin.mallapi.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyin.mallapi.qo.CategoryQo;

import java.util.Map;

/**
 * <p>
 * 类别表 服务类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-01
 */
public interface ICategoryService extends IService<Category> {
    //条件分页查询方法
    IPage<Category> page(CategoryQo qo);

    //添加
    void save(Map<String,Object> map);

}
