package com.example.demo.controller;

import com.example.demo.common.CommonResult;
import com.example.demo.entity.User;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiusuyang
 * @since 2023-08-22
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping( "/login")
    @ResponseBody
    public CommonResult<User> login(String username, String password, HttpSession session){
        System.out.println(username + password);
        CommonResult<User> rs = userService.login(username,password);
        if(rs.getCode()==0){
            session.setAttribute("user",rs.getData());
        }
        return rs;

    }
    @GetMapping("/login")
    public String lg(){
        return "login";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/singout")
    @ResponseBody
    private CommonResult<Boolean> singout(HttpSession session){
        CommonResult<Boolean> rs=new CommonResult<>();
        session.removeAttribute("user");
        rs.setMsg("删除成功！");
        rs.setCode(0);
        return rs;
    }

    @GetMapping("/markers")
    public String markers(){
        return "markers/index";
    }

    @GetMapping("/scene")
    public String scene(){ return "scene/index";}
}
