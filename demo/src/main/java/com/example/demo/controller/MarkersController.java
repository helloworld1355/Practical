package com.example.demo.controller;

import com.example.demo.common.CommonResult;
import com.example.demo.entity.Markers;
import com.example.demo.entity.Scene;
import com.example.demo.service.impl.MarkersServiceImpl;
import com.example.demo.service.impl.SceneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
@RequestMapping("/markers")
public class MarkersController {
    @Autowired
    private MarkersServiceImpl service;
    @Autowired
    private SceneServiceImpl sceneService;

    @Value("${host-path}")
    private String hostPath;

    @Value("${image-height}")
    private double imageHeight;

    @Value("${image-width}")
    private double imageWidth;


    @GetMapping("/getAll")
    @ResponseBody
    public CommonResult<List<Markers>> getAll(){
        return  service.getAll();
    }

        @GetMapping("/form")
        public String edit(@RequestParam int id, Model model){
            System.out.println("进入form接口");
            if(id==0){
                Markers markers=new Markers();
                markers.setId(0);
                Scene scene=new Scene();
                scene.setId(0);
                model.addAttribute("markers",markers);
                model.addAttribute("scene",scene);
            }else{
                model.addAttribute("markers",service.getById(id));
                model.addAttribute("scene",sceneService.getByid(id));
            }

            return "markers/form";
        }


    @GetMapping("/search")
    @ResponseBody
    public CommonResult<List<Markers>> search(String title){
        System.out.println("进入search");
        return service.search(title);
    }

    @PostMapping("/submit")
    @ResponseBody
    public CommonResult<Boolean> submit(Integer id,String title,String address,String content,
                                        double radius,String audio,String imgPath,String latitude,String longitude){
        System.out.println("进入submit");
        CommonResult<Boolean> rs=new CommonResult<>();
        audio=hostPath+"/audio/"+audio;
        imgPath=hostPath+"/img/"+imgPath;
        Markers markers=new Markers();
        markers.setId(id);
        markers.setTitle(title);
        markers.setIconPath(imgPath);
        markers.setLatitude(latitude);
        markers.setLongitude(longitude);
        markers.setHeight(imageHeight);
        markers.setWidth(imageWidth);
        rs=service.saveEntity(markers);
        Scene scene=new Scene();
        scene.setId(id);
        scene.setAudio(audio);
        scene.setRadius(radius);
        scene.setContent(content);
        scene.setAddress(address);
        sceneService.saveEntity(scene);

        return rs;
    }

    @PostMapping("/del")
    @ResponseBody
    public CommonResult<Boolean> del(Integer id){
        System.out.println("进入del");
        sceneService.del(id);
        return service.del(id);
    }


}
