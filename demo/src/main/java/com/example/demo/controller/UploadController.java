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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/upload")
public class UploadController {
    @Value("${image-path}")
    private String img_path;

    @Value("${audio-path}")
    private String audio_path;

    @Value("${host-path}")
    private String host;

    @PostMapping("/img")
    @ResponseBody
    public CommonResult<String> image(@RequestParam("file") MultipartFile file){
        System.out.println("进入上传图片");
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

    @PostMapping("/kindImage")
    @ResponseBody
    public Map kindImage(@RequestParam("file") MultipartFile file){
        System.out.println("进入kidn上传图片");
        Map result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("error", 1);
            result.put("message", "文件为空");
            return result;
        }

        try {
            // 保存上传的图片到指定目录
            String uploadPath = img_path;
            String filename = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File dest = new File(uploadPath + filename);
            file.transferTo(dest);

            // 返回上传成功的图片路径
            String imageUrl = host+"/img/" + filename;
            result.put("error", 0);
            result.put("url", imageUrl);
            System.out.println(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result.put("error", 0);
            result.put("message", "上传失败");
            return result;
        }
    }
}
