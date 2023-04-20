package com.sfh.shopping.controller;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.Category;
import com.sfh.shopping.model.Goods;
import com.sfh.shopping.model.GoodsSearchBean;
import com.sfh.shopping.service.CategoryService;
import com.sfh.shopping.service.GoodsService;
import com.sfh.shopping.util.PaginateInfo;
import com.sfh.shopping.util.SliceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    private final CategoryService service;
    private GoodsService goodService;

    public IndexController(CategoryService service) {
        this.service = service;
    }

    @Autowired
    public void setGoodService(GoodsService goodService) {
        this.goodService = goodService;
    }

    @RequestMapping({"", "/index"})
    public String index(Map<String, Object> map) {
        List<Category> list = service.findAllRoots();
        map.put("roots", list);//一级分类

        //查询所有热销商品
        PaginateInfo pi = new PaginateInfo(1, 10);
        GoodsSearchBean gsb = new GoodsSearchBean();
        gsb.setBestSeller(true);//热销
        gsb.setIsDelete(false);//未删除
        gsb.setTakeOff(false);//未下架

        List<Goods> bestSellers = goodService.findAll(gsb, pi);

        map.put("bestSellers", bestSellers);

        //查询新品上架
        pi = new PaginateInfo(1, 30);
        gsb.setBestSeller(null);//取消热销
        gsb.setNewProduct(true);//新品

        List<Goods> newProducts = goodService.findAll(gsb, pi);
        //分组
        int cnt = newProducts.size() / 3;
        if (newProducts.size() % 3 > 0) {
            cnt++;
        }

        List<List<Goods>> groups = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            groups.add(SliceUtils.slice(newProducts, 3 * i, 3));
        }
        map.put("newProductGroups", groups);//几组商品


        map.put("upload_server_url", Global.UPLOAD_SERVER_URL);
        return "index";
    }
}
