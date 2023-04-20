package com.sfh.shopping.service;

import com.sfh.shopping.model.Brand;
import com.sfh.shopping.util.PaginateInfo;

import java.util.List;

public interface BrandService {
    List<Brand> findAll(Brand brand, PaginateInfo pi);

    boolean save(Brand brand);

    Brand findById(Integer id);

    boolean update(Brand brand);

    int deleteByIds(Integer ids[]);
}
