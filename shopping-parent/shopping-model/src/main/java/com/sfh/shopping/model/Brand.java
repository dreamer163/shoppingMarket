package com.sfh.shopping.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName t_goods_brand
 */
@Data
public class Brand implements Serializable {
    /**
     * 品牌id
     */
    private Integer id;

    /**
     * 品牌名
     */
    private String name;

    /**
     * 品牌图片地址
     */
    private String picUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String description;

    private static final long serialVersionUID = 1L;
}