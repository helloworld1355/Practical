package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.CommonResult;
import com.example.demo.entity.Markers;
import com.example.demo.entity.Scene;
import com.example.demo.mapper.SceneMapper;
import com.example.demo.service.ISceneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiusuyang
 * @since 2023-08-22
 */
@Service
public class SceneServiceImpl extends ServiceImpl<SceneMapper, Scene> implements ISceneService {
    @Autowired
    private SceneMapper mapper;

    @Override
    @Cacheable(value = "getScenes")
    public CommonResult<List<Scene>> getAll(){
        System.out.println("进入Scene ALL");
        CommonResult<List<Scene>> commonResult=new CommonResult<>();
        QueryWrapper<Scene> wrapper=new QueryWrapper<>();
        List<Scene> list=mapper.selectList(wrapper.select());
        if(list==null){
            commonResult.setCode(-1);
            commonResult.setMsg("数据库中无信息！！");
        }else{
            commonResult.setCode(0);
            commonResult.setMsg("成功！");
            commonResult.setData(list);
            commonResult.setCount(list.size());
        }

        return commonResult;
    }

    @Override
    public CommonResult<List<Scene>> search(String address,String audio,String content){
        CommonResult<List<Scene>> rs=new CommonResult<>();
        QueryWrapper<Scene> wrapper=new QueryWrapper<>();
        List<Scene> list=new ArrayList<>();
        System.out.println("进入search");
        if(!address.equals("")){
            list.addAll(mapper.selectList(wrapper.like("address",address)));
            System.out.println("address=="+list);
        }
        if(!audio.equals("")){
            list.addAll(mapper.selectList(wrapper.like("audio",audio)));
            System.out.println("audio=="+list);
        }
        if(!content.equals("")){
            list.addAll(mapper.selectList(wrapper.like("content",content)));
            System.out.println("content=="+list);
        }
        if(list.size()!=0){
            rs.setCode(0);
            rs.setData(list);
            rs.setMsg("成功");
            rs.setCount(list.size());
        }else {
            rs.setCode(-1);
            rs.setMsg("查询失败！未找到数据");
        }
        return rs;

    }

    @Override
    @CacheEvict(value = "getScenes",allEntries = true)
/**未完成--消息rs未设置 */
    public CommonResult<List<Scene>> del(Integer id){
        CommonResult<List<Scene>> rs=new CommonResult<>();
        mapper.deleteById(id);
        getAll();
        return rs;
    }

    @Override
    public Scene getByid(Integer id){
        return mapper.selectById(id);
    }


    @Override
    @CacheEvict(value = "getScenes",allEntries = true)
    public CommonResult<Boolean> saveEntity(Scene scene){
        CommonResult<Boolean> rs=new CommonResult<>();
        try{
            if(scene.getId()==0){
                int k=mapper.insert(scene);
                if(k>0){
                    rs.setMsg("添加成功");
                    rs.setCode(0);
                }else {
                    rs.setCode(-1);
                    rs.setMsg("添加失败");
                    rs.setData(false);
                }
            }else{
                int k = mapper.updateById(scene);
                if (k > 0) {
                    rs.setCode(0);
                    rs.setMsg("修改成功");
                    rs.setData(true);
                } else {
                    rs.setCode(-1);
                    rs.setMsg("修改失败");
                    rs.setData(false);
                }
            }
        }catch (Exception e){
            rs.setCode(-1);
            rs.setMsg("发生错误:"+e.getMessage());
        }
        getAll();
        return rs;
    }
}
