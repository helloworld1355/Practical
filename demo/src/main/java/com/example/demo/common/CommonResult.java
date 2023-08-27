package com.example.demo.common;

import lombok.Data;

import java.io.Serializable;


@Data
public class CommonResult<T> implements Serializable {
    private Integer code;// 返回码
    private String msg;//返回消息
    private long count;//分页时用到，用于存储总记录数
    private T data;// 返回的数据
}
