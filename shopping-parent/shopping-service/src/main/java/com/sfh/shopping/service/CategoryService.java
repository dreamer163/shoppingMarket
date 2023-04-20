package com.sfh.shopping.service;

import com.sfh.shopping.model.Category;
import com.sfh.shopping.util.PaginateInfo;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    List<Category> findAllRoots();

    Category findById(Integer id);

    boolean save(Category category);

    boolean update(Category category);

    int deleteByIds(Integer[] ids);
}

