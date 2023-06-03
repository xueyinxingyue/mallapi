package com.xueyin.mallapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyin.mallapi.entity.Category;
import com.xueyin.mallapi.entity.Property;
import com.xueyin.mallapi.mapper.CategoryMapper;
import com.xueyin.mallapi.mapper.PropertyMapper;
import com.xueyin.mallapi.qo.CategoryQo;
import com.xueyin.mallapi.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类别表 服务实现类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-01
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public IPage<Category> page(CategoryQo qo) {
        //设置条件
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(qo.getQuery()),Category::getCategoryName,qo.getQuery());

        //设置分页信息
        IPage<Category> page = new Page<>(qo.getPageNum(), qo.getPageSize());

        //查询
        page = baseMapper.selectPage(page,wrapper);

        return page;
    }

    @Override
    public void save(Map<String,Object> map) {
        //保存分类信息
        String categoryName = (String) map.get("categoryName");
        Category category = new Category();
        category.setCategoryName(categoryName);
        baseMapper.insert(category);

        //保存分类属性信息
        @SuppressWarnings("unchecked")
        List<String> props = (List<String>) map.get("props");

        //判断是否有属性
        if (props != null && props.size() > 0){
            for (String prop : props) {
                //将属性信息添加到property表中
                Property property = new Property();
                property.setPropertyName(prop);
                property.setPropertyCategoryId(category.getCategoryId());
                propertyMapper.insert(property);
            }
        }
    }

    @Override
    public Category getById(Serializable id) {
        //查询到分类信息
        Category category = baseMapper.selectById(id);

        //查询分类的属性信息
        LambdaQueryWrapper<Property> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Property::getPropertyCategoryId,id);
        List<Property> propertyList = propertyMapper.selectList(wrapper);

        //将属性信息放到分类对象中
        category.setProps(propertyList);
        return category;
    }

    @Override
    public boolean updateById(Category entity) {
        //修改分类信息
        baseMapper.updateById(entity);

        //修改分类的属性信息
        //1.将原有的属性删除
        LambdaUpdateWrapper<Property> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Property::getPropertyCategoryId,entity.getCategoryId());
        propertyMapper.delete(wrapper);
        //2.在将获取到的属性添加到数据库
        List<Property> props = entity.getProps();
        if (props != null && props.size() > 0){
            for (Property property : props){
                //为property设置分类ID
                property.setPropertyCategoryId(entity.getCategoryId());
                propertyMapper.insert(property);
            }
        }
        return false;
    }

    @Override
    public boolean removeById(Serializable id) {
        //删除分类信息
        baseMapper.deleteById(id);

        //删除分类下所有属性信息
        LambdaUpdateWrapper<Property> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Property::getPropertyCategoryId,id);
        propertyMapper.delete(wrapper);

        return false;
    }
}
