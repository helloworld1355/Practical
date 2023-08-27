package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.io.Serializable;

@Data
@TableName("scene")
public class Scene implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private String address;
    private String audio;
    private String content;
    private double radius;
}
