package com.sfh.shopping.dao;

import com.sfh.shopping.model.Category;
import com.sfh.shopping.util.PaginateInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDAO {

    List<Category> findAll();

    Category findById(Integer id);

    int save(Category category);

    int update(Category category);

    int deleteByIds(Integer[] ids);
}
