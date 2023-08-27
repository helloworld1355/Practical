package com.example.demo.service;

import com.example.demo.common.CommonResult;
import com.example.demo.entity.Scene;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiusuyang
 * @since 2023-08-22
 */
public interface ISceneService extends IService<Scene> {
    public CommonResult<List<Scene>> getAll();
    public CommonResult<List<Scene>> search(String address,String audio,String content);
    public CommonResult<List<Scene>> del(Integer id);
    public Scene getByid(Integer id);
    public CommonResult<Boolean> saveEntity(Scene scene);

}
