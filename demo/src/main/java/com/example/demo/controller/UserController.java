package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/markers")
    public String markers(){
        return "markers/index";
    }

    @GetMapping("/scene")
    public String scene(){ return "scene/index";}
}
