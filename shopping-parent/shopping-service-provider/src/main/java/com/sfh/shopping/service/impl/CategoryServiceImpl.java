package com.sfh.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfh.shopping.dao.CategoryDAO;
import com.sfh.shopping.model.Brand;
import com.sfh.shopping.model.Category;
import com.sfh.shopping.service.CategoryService;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO dao;

    public CategoryServiceImpl(CategoryDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Category> findAll() {
       return dao.findAll();
    }


    @Override

    public List<Category> findAllRoots() {
       //CategoryService cs = (CategoryService) AopContext.currentProxy();
        List<Category> list = this.findAll();
        return list.stream().filter(t -> t.getParent() == null).toList();
    }

    @Override
    public Category findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public boolean save(Category category) {
        return dao.save(category) > 0;
    }


    @Override
    public boolean update(Category category) {
        return dao.update(category) > 0;
    }


    @Override
    public int deleteByIds(Integer[] ids) {
        return dao.deleteByIds(ids);
    }
}
