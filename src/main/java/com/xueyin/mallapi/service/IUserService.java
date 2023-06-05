package com.xueyin.mallapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyin.mallapi.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyin.mallapi.qo.UserQo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xueyin
 * @since 2023-06-05
 */
public interface IUserService extends IService<User> {
    public IPage<User> page(UserQo qo);
}
