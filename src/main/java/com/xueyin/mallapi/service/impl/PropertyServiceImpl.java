package com.xueyin.mallapi.service.impl;

import com.xueyin.mallapi.entity.Property;
import com.xueyin.mallapi.mapper.PropertyMapper;
import com.xueyin.mallapi.service.IPropertyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 类别属性表 服务实现类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-01
 */
@Service
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, Property> implements IPropertyService {

}
