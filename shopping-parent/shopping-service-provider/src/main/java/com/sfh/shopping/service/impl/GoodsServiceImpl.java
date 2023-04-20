package com.sfh.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfh.shopping.dao.GoodsDAO;
import com.sfh.shopping.model.Goods;
import com.sfh.shopping.model.GoodsSearchBean;
import com.sfh.shopping.service.GoodsService;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsDAO dao;

    public GoodsServiceImpl(GoodsDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Goods> findAll(GoodsSearchBean gsb, PaginateInfo pi) {
        if (pi != null) {
            PageHelper.startPage(pi.getPageNo(), pi.getPageSize());//启用分页
        }
        List<Goods> goods = dao.findAll(gsb);

        if (pi != null) {
            PageInfo<Goods> page = new PageInfo<>(goods);
            pi.setTotal((int) page.getTotal());
        }
        return goods;
    }

    @Override
    public List<Goods> findAll(GoodsSearchBean gsb) {
        GoodsService gs =(GoodsService)AopContext.currentProxy();
        return gs.findAll(gsb,null);
    }

    @Override
    public Goods findById(Integer id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public boolean add(Goods goods) {
        goods.setCreateTime(LocalDateTime.now());
        return dao.insert(goods) > 0;
    }

    @Override
    public boolean update(Goods goods) {
        goods.setUpdateTime(LocalDateTime.now());
        return dao.updateByPrimaryKeySelective(goods) > 0;
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        int count = 0;
        for (Integer id:ids) {
            Goods goods = new Goods();
            goods.setId(id);
            goods.setIsDelete(true);

            dao.updateByPrimaryKeySelective(goods);
            count++;
        }
        return count;
    }
}
