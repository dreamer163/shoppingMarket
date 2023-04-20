package com.sfh.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfh.shopping.dao.BrandDAO;
import com.sfh.shopping.model.Brand;
import com.sfh.shopping.model.Goods;
import com.sfh.shopping.model.GoodsSearchBean;
import com.sfh.shopping.model.User;
import com.sfh.shopping.service.BrandService;
import com.sfh.shopping.service.GoodsService;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandDAO dao;

    private GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    public BrandServiceImpl(BrandDAO dao) {
        this.dao = dao;
    }

    //查询全部
    @Override
    public List<Brand> findAll(Brand brand, PaginateInfo pi) {
        if (pi != null) {
            PageHelper.startPage(pi.getPageNo(), pi.getPageSize());//启用分页
        }
        List<Brand> brands = dao.findAll(brand);

        if (pi != null) {
            PageInfo<Brand> page = new PageInfo<>(brands);
            pi.setTotal((int) page.getTotal());
        }
        return brands;
    }

    @Override
    public boolean save(Brand brand) {
        brand.setCreateTime(LocalDateTime.now());
        return dao.insert(brand) > 0;
    }

    @Override
    public Brand findById(Integer id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public boolean update(Brand brand) {
        brand.setUpdateTime(LocalDateTime.now());
        return dao.updateByPrimaryKeySelective(brand) > 0;
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        int count = 0;
        for (Integer id : ids) {
            this.deleteById(id);
            count++;
        }
        return count;
    }


    //删除一个品牌，但要确认是否有对应品牌的产品，有的话不删除
    private int deleteById(Integer id) {
        GoodsSearchBean gsb = new GoodsSearchBean();
        Brand brand = new Brand();
        brand.setId(id);
        gsb.setBrand(brand);
        //去goods数据库查有没有商品属于这个品牌
        List<Goods> goods =goodsService.findAll(gsb);
        if (goods.size()>0){
            //有属于这个品牌的商品，不进行删除品牌操作
            return 0;
        }else {
            return dao.deleteById(id);//删除品牌，真正删除
        }
    }
}
