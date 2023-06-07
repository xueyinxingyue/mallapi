package com.xueyin.mallapi.service.impl;

import com.xueyin.mallapi.entity.Cart;
import com.xueyin.mallapi.mapper.CartMapper;
import com.xueyin.mallapi.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-07
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

}
