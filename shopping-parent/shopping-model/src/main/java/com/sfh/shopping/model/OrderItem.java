package com.sfh.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItem implements Serializable {
    private Integer id;

    private String orderId;

    private Goods good;

    private String goodName;//缓存的商品名

    private String goodPic;//商品图片

    private BigDecimal price;

    private Integer amount;

    private BigDecimal total;//小计
}