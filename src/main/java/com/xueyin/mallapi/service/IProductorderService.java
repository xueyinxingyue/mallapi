package com.xueyin.mallapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyin.mallapi.entity.Productorder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyin.mallapi.qo.ProductOrderQo;

/**
 * <p>
 * 产品订单表 服务类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-07
 */
public interface IProductorderService extends IService<Productorder> {
    IPage<Productorder> page(ProductOrderQo qo);
}
