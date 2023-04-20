package com.sfh.shopping.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sfh.shopping.util.GoodsSerializer;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 * @TableName t_goods
 */

@Data
@JsonSerialize(using = GoodsSerializer.class)
public class Goods implements Serializable {
    private Integer id;

    /**
     * 商品图片地址
     */
    private String picUrl;

    /**
     * 商品编号
     */
    private String spuNo;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品所属类别
     */
    private Category category;

    /**
     * 商品品牌
     */
    private Brand brand;

    /**
     * 建议售价
     */
    private BigDecimal markPrice;

    /**
     * 实际售价
     */
    private BigDecimal price;

    /**
     * 商品产地
     */
    private String place;

    /**
     * 是否热销
     */
    private Boolean bestSeller;

    /**
     * 是否新品
     */
    private Boolean newProduct;

    /**
     * 是否下架
     */
    private Boolean takeOff;

    /**
     * 详情
     */
    private Boolean isDelete;
    private String description;

    /**
     * 记录创建时间
     */
    private LocalDateTime createTime;

    /**
     * 记录更新时间
     */
    private LocalDateTime updateTime;

    /**
     *
     * 富文本编辑器的内容细节
     */

    private String detail;
}