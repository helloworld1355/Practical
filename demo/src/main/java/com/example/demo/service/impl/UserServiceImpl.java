package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.CommonResult;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiusuyang
 * @since 2023-08-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper mapper;

    @Override
    public CommonResult<User> login(String name, String password){
        System.out.println("input :"+name+"==="+password);
        CommonResult<User> rs=new CommonResult<>();
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        User user=mapper.selectOne(wrapper.eq("name",name));
        System.out.println("user="+user);
        if(user!=null){
            System.out.println(user.getPassword().equals(password));
            if(user.getPassword().equals(password)){
                rs.setCode(0);
                rs.setData(user);
            }else {
                rs.setCode(-1);
                rs.setData(null);
                rs.setMsg("密码错误！请重新输入");
            }
        }else {
            rs.setCode(-1);
            rs.setMsg("未找到该用户！");
        }
        return rs;
    }
}
