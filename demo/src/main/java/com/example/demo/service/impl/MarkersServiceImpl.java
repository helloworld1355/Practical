package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.CommonResult;
import com.example.demo.entity.Markers;
import com.example.demo.mapper.MarkersMapper;
import com.example.demo.service.IMarkersService;
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
public class MarkersServiceImpl extends ServiceImpl<MarkersMapper, Markers> implements IMarkersService {
    @Autowired
    private MarkersMapper mapper;

    @Override
    @Cacheable(value = "getMarkers")
    public CommonResult<List<Markers>> getAll(){
        System.out.println("进入Markser all");
        System.out.println("更新缓存或者首次进入");
        CommonResult<List<Markers>> rs=new CommonResult<>();
        List<Markers> list=mapper.selectList(new QueryWrapper<Markers>().select());
        if(list.size()!=0){
            rs.setCode(0);
            rs.setCount(list.size());
            rs.setData(list);
            rs.setMsg("成功！");
        }else{
            rs.setCode(-1);
            rs.setMsg("无数据！");
        }
        System.out.println("getAll=="+rs);
        return rs;
    }

    @Override
    @CacheEvict(value = "getMarkers",allEntries = true)
    public CommonResult<Boolean> saveEntity(Markers markers){
        System.out.println("进入保存markers");
        CommonResult<Boolean> rs=new CommonResult<>();
        try{
            if(markers.getId()==0){
                int k=mapper.insert(markers);
                if(k>0){

                rs.setMsg("添加成功");
                rs.setCode(0);
                }else {
                    rs.setCode(-1);
                    rs.setMsg("添加失败");
                    rs.setData(false);
                }
            }else{
                int k = mapper.updateById(markers);
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

    @Override
    public CommonResult<List<Markers>> search(String title){
        CommonResult<List<Markers>> rs=new CommonResult<>();
        List<Markers> list=new ArrayList<>();
        QueryWrapper<Markers> wrapper=new QueryWrapper<>();
        list=mapper.selectList(wrapper.like("title",title));
        if(!title.equals("")){
            if(list.size()!=0){
                rs.setCode(0);
                rs.setCount(list.size());
                rs.setData(list);
                rs.setMsg("搜索成功！");
            }else {
                rs.setMsg("未找到数据！");
                rs.setCode(0);
            }
        }else {
            rs.setCode(0);
            rs.setMsg("请输入搜索字段！");
            rs.setData(list);
            rs.setCount(list.size());
        }
        return rs;
    }

    @Override
    @CacheEvict(value = "getMarkers",allEntries = true)
    public CommonResult<Boolean> del(Integer id){
        CommonResult<Boolean> rs=new CommonResult<>();
        try{
            int k=mapper.deleteById(id);
            if(k>0){
                rs.setMsg("删除成功");
                rs.setCode(0);
            }else {
                rs.setMsg("删除失败");
                rs.setCode(-1);
            }
        }catch (Exception e){
            rs.setCode(-1);
            rs.setMsg("发生错误："+e.getMessage());
        }
        getAll();
        return rs;
    }


}
