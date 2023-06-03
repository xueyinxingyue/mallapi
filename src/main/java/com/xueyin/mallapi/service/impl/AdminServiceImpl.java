package com.xueyin.mallapi.service.impl;

import com.xueyin.mallapi.entity.Admin;
import com.xueyin.mallapi.mapper.AdminMapper;
import com.xueyin.mallapi.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author xueyin
 * @since 2023-05-31
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
