package com.sfh.shopping.model;

import lombok.Data;

@Data
public class CartItem {
    private Integer id;
    private Integer userId;
    private Goods good;
    private Integer amount;
}
