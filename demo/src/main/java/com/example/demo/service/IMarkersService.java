package com.example.demo.service;

import com.example.demo.common.CommonResult;
import com.example.demo.entity.Markers;
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
public interface IMarkersService extends IService<Markers> {
    public CommonResult<List<Markers>> getAll();
    public CommonResult<Boolean> saveEntity(Markers markers);
    public CommonResult<List<Markers>> search(String title);
    public CommonResult<Boolean> del(Integer id);


}
