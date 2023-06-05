package com.xueyin.mallapi.service.impl;

import com.xueyin.mallapi.entity.Address;
import com.xueyin.mallapi.mapper.AddressMapper;
import com.xueyin.mallapi.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址表 服务实现类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-05
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
