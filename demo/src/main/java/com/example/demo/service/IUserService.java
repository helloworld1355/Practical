package com.example.demo.service;

import com.example.demo.common.CommonResult;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiusuyang
 * @since 2023-08-22
 */
public interface IUserService extends IService<User> {
    public CommonResult<User> login(String name,String password);
}
