package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("markers")
public class Markers  implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private String title;
    private String latitude;
    private String longitude;
    private String iconPath;
    private double width;
    private double height;

}
