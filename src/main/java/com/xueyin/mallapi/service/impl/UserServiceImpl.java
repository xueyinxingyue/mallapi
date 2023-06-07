package com.xueyin.mallapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyin.mallapi.entity.Address;
import com.xueyin.mallapi.entity.Cart;
import com.xueyin.mallapi.entity.Product;
import com.xueyin.mallapi.entity.User;
import com.xueyin.mallapi.mapper.AddressMapper;
import com.xueyin.mallapi.mapper.CartMapper;
import com.xueyin.mallapi.mapper.ProductMapper;
import com.xueyin.mallapi.mapper.UserMapper;
import com.xueyin.mallapi.qo.UserQo;
import com.xueyin.mallapi.service.IProductService;
import com.xueyin.mallapi.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private IProductService productService;

    @Override
    public IPage<User> page(UserQo qo) {
        //条件分页查询用户信息
        LambdaQueryWrapper<User> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.like(StringUtils.isNotBlank(qo.getKeyword()),User::getUserName,qo.getKeyword())
                .or()
                .like(StringUtils.isNotBlank(qo.getKeyword()),User::getUserNickname,qo.getKeyword());
        IPage<User> page = new Page<>(qo.getPageNum(), qo.getPageSize());

        page = baseMapper.selectPage(page, wrapper1);

        //获取page中每一个用户的详细地址信息
        List<User> userList = page.getRecords();
        if (userList != null && userList.size() > 0){
            for (User user : userList){
                //获取所在地的详细地址信息
                //获取当前记录的地址编码对应的地址对象
                Address address = addressMapper.selectById(user.getUserAddress());      //当前地址对象    未央区
//                address = addressMapper.selectById(address.getAddressRegionid());                 //西安市
//                address = addressMapper.selectById(address.getAddressRegionid());                             //陕西省
                //如果当前地址编码与父级编码不一致，说明还有上一级
                String addressNameDetail = address.getAddressName();
                while (!address.getAddressAreaid().equals(address.getAddressRegionid())){
                    address = addressMapper.selectById(address.getAddressRegionid());   //父级地址对象
                    //将地址信息拼接到详细地址信息中
                    addressNameDetail = address.getAddressName() + " " + addressNameDetail;
                }
                //将详细地址设置到address
                address.setAddressName(addressNameDetail);
                //将地址对象设置给user的用户所在地地址对象
                user.setUserAddressObj(address);

                //获取家乡详细地址信息
                String userHomeplaceAddressId = user.getUserHomeplace();
                Address homeAddressObj = addressMapper.selectById(userHomeplaceAddressId);
                String homeAddressNameDetail = homeAddressObj.getAddressName();
                while (!homeAddressObj.getAddressAreaid().equals(homeAddressObj.getAddressRegionid())){
                    homeAddressObj = addressMapper.selectById(homeAddressObj.getAddressRegionid());
                    homeAddressNameDetail = homeAddressObj.getAddressName() + " " + homeAddressNameDetail;
                }
                homeAddressObj.setAddressName(homeAddressNameDetail);
                user.setUserHomeAddressObj(homeAddressObj);

                //查询用户的购物车信息
                LambdaQueryWrapper<Cart> wrapper2 = new LambdaQueryWrapper<>();
                wrapper2.eq(Cart::getCartUserId,user.getUserId());
                List<Cart> carts = cartMapper.selectList(wrapper2);

                //查询购物车信息中商品的信息
                if (carts != null && carts.size() > 0){
                    //查询购物车信息中商品的信息
                    for (Cart cart : carts){
                        Product product = productService.getById(cart.getCartProductId());
                        //将查询到的商品信息存储到购物车项对象中
                        cart.setProduct(product);
                    }
                }

                //将购物车信息存放到user对象
                user.setCarts(carts);
            }
        }

        return page;
    }
}
