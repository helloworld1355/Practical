package com.example.demo.controller;

import com.example.demo.common.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/upload")
public class UploadController {
    @Value("${image-path}")
    private String img_path;

    @Value("${audio-path}")
    private String audio_path;

    @PostMapping("/img")
    @ResponseBody
    public CommonResult<String> image(@RequestParam("file") MultipartFile file){
        CommonResult<String> rs =new CommonResult<>();
        String preName=file.getOriginalFilename();
        System.out.println(preName);
        System.out.println(img_path);

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String k = sdf.format(new Date());
            System.out.println(k);
            file.transferTo(new File(img_path+ File.separator + k + preName));
            rs.setCode(0);
            rs.setMsg("上传成功");
            rs.setData( k + preName);

        }catch (IOException e){
            e.printStackTrace();
            rs.setCode(-1);
            rs.setMsg("上传失败" + e.getMessage());
            return rs;
        }
        return rs;
    }

    @PostMapping("/audio")
    @ResponseBody
    public CommonResult<String> audio(@RequestParam("file") MultipartFile file){
        CommonResult<String> rs=new CommonResult<>();
        String preName=file.getOriginalFilename();
        System.out.println(preName);
        System.out.println(audio_path);

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String k = sdf.format(new Date());
            System.out.println(k);
            file.transferTo(new File(audio_path+ File.separator + k + preName));
            rs.setCode(0);
            rs.setMsg("上传成功");
            rs.setData( k + preName);
            System.out.println(rs);
        }catch (IOException e){
            e.printStackTrace();
            rs.setCode(-1);
            rs.setMsg("上传失败" + e.getMessage());
            System.out.println(rs);
            return rs;
        }
        return rs;
    }

}
