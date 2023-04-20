package com.sfh.shopping.service;

import com.sfh.shopping.model.Goods;
import com.sfh.shopping.model.GoodsSearchBean;
import com.sfh.shopping.util.PaginateInfo;

import java.util.List;

public interface GoodsService {
    List<Goods> findAll(GoodsSearchBean gsb, PaginateInfo pi);

    List<Goods> findAll(GoodsSearchBean gsb);
    Goods findById(Integer id);

    boolean add(Goods goods);

    boolean update(Goods goods);

    int deleteByIds(Integer[] ids);
}
