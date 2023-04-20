package com.sfh.shopping.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sfh.shopping.util.CategorySerializer;
import lombok.Data;

import java.util.List;
//自定义序列化器

@Data
@JsonSerialize(using = CategorySerializer.class)
public class Category {
    private Integer id;
    private String name;
    private String iconClass;
    private Category parent;
    private List<Category> children;
    private Integer seq;
    private String description;
}
