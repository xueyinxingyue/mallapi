package com.xueyin.mallapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyin.mallapi.entity.Productorder;
import com.xueyin.mallapi.mapper.ProductorderMapper;
import com.xueyin.mallapi.qo.ProductOrderQo;
import com.xueyin.mallapi.service.IProductorderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品订单表 服务实现类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-07
 */
@Service
public class ProductorderServiceImpl extends ServiceImpl<ProductorderMapper, Productorder> implements IProductorderService {

    @Override
    public IPage<Productorder> page(ProductOrderQo qo) {
        LambdaQueryWrapper<Productorder> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(qo.getKeyword()),Productorder::getProductorderCode,qo.getKeyword())
                .in(qo.getOrderStatus() != null && qo.getOrderStatus().size() > 0,Productorder::getProductorderStatus,qo.getOrderStatus());

        IPage<Productorder> page = new Page<>(qo.getPageNum(), qo.getPageSize());

        page = baseMapper.selectPage(page,wrapper);
        return page;
    }
}
