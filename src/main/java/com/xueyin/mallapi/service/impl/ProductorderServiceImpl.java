package com.xueyin.mallapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyin.mallapi.entity.*;
import com.xueyin.mallapi.mapper.AddressMapper;
import com.xueyin.mallapi.mapper.ProductorderMapper;
import com.xueyin.mallapi.mapper.ProductorderitemMapper;
import com.xueyin.mallapi.mapper.UserMapper;
import com.xueyin.mallapi.qo.ProductOrderQo;
import com.xueyin.mallapi.service.IProductService;
import com.xueyin.mallapi.service.IProductorderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ProductorderitemMapper productorderitemMapper;
    @Autowired
    private IProductService productService;

    @Override
    public IPage<Productorder> page(ProductOrderQo qo) {
        LambdaQueryWrapper<Productorder> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(qo.getKeyword()),Productorder::getProductorderCode,qo.getKeyword())
                .in(qo.getOrderStatus() != null && qo.getOrderStatus().size() > 0,Productorder::getProductorderStatus,qo.getOrderStatus());

        IPage<Productorder> page = new Page<>(qo.getPageNum(), qo.getPageSize());

        page = baseMapper.selectPage(page,wrapper);
        return page;
    }

    @Override
    public Productorder getById(Serializable id) {
        //查询订单信息
        Productorder productorder = baseMapper.selectById(id);

        //查询订单所属的用户信息
        User user = userMapper.selectById(productorder.getProductorderUserId());
        //将用户信息存储到订单对象中
        user.setUserPassword("");
        productorder.setUser(user);

        //查询订单收货地址详细地址信息
        String addressCode = productorder.getProductorderAddress();
        Address address = addressMapper.selectById(addressCode);
        String addressName = address.getAddressName();
        while (!address.getAddressAreaid().equals(address.getAddressRegionid())){
            address = addressMapper.selectById(address.getAddressRegionid());
            addressName = address.getAddressName() + " " + addressName;
        }
        address.setAddressName(addressName);
        productorder.setProductorderAddressObj(address);

        //查询订单中订单项信息
        LambdaQueryWrapper<Productorderitem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Productorderitem::getProductorderitemOrderId,id);
        List<Productorderitem> productorderitems = productorderitemMapper.selectList(wrapper);
        //查询每一个订单项的商品信息
        if (productorderitems != null && productorderitems.size() > 0){
            for (Productorderitem productorderitem : productorderitems){
                Product product = productService.getById(productorderitem.getProductorderitemProductId());
                productorderitem.setProduct(product);
            }
        }
        //将查询到的订单项信息存储到订单对象中
        productorder.setProductorderitems(productorderitems);

        return productorder;
    }
}
