package com.sfh.shopping.model;

import lombok.Data;

@Data
public class GoodsFormBean extends Goods{
    private Integer brandId;
    private  Integer categoryId;

}
