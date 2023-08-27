package com.example.demo.controller;

import com.example.demo.common.CommonResult;
import com.example.demo.entity.Scene;
import com.example.demo.service.impl.SceneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiusuyang
 * @since 2023-08-22
 */
@Controller
@RequestMapping("/scene")
public class SceneController {
    @Autowired
    private SceneServiceImpl service;

    @GetMapping("/getAll")
    @ResponseBody
    public CommonResult<List<Scene>> getAll(){
        return service.getAll();
    }

    @GetMapping("/search")
    @ResponseBody
    public CommonResult<List<Scene>> search(String address,String audio,String content){
        System.out.println("收到search请求,adress:"+address+"+++audio:"+audio+"+++content:"+content);
        CommonResult<List<Scene>> rs=new CommonResult<>();
        rs=service.search(address,audio,content);
        if(rs.getData()==null){
            rs=getAll();
        }
        return rs;

    }

    @PostMapping("/del")
    @ResponseBody
    public CommonResult<List<Scene>> del(Integer id){
        return service.del(id);
    }

    @GetMapping("/form")
    public String edit(@RequestParam int id, Model model){
        System.out.println("进入form接口");
        model.addAttribute("model",service.getByid(id));
        return "markers/form";
    }
}
