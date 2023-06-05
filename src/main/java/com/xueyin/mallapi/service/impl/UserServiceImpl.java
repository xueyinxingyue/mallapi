package com.xueyin.mallapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyin.mallapi.entity.User;
import com.xueyin.mallapi.mapper.UserMapper;
import com.xueyin.mallapi.qo.UserQo;
import com.xueyin.mallapi.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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

            }
        }

        return page;
    }
}
