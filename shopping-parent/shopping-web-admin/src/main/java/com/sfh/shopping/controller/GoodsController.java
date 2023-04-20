package com.sfh.shopping.controller;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.Brand;
import com.sfh.shopping.model.Category;
import com.sfh.shopping.model.Goods;
import com.sfh.shopping.service.BrandService;
import com.sfh.shopping.service.CategoryService;
import com.sfh.shopping.service.GoodsService;
import com.sfh.shopping.util.PaginateInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import java.util.Map;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService service;

    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    public GoodsController(GoodsService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String list(Map<String, Object> map) {
        map.put("upload_server_url", Global.UPLOAD_SERVER_URL);
        return "goods/list";
    }


    @GetMapping("/add")
    public String add(Map<String, Object> map) {
        List<Brand> brands = brandService.findAll(null, new PaginateInfo(1, Integer.MAX_VALUE));
        map.put("brands", brands);

        List<Category> categories = categoryService.findAll();
        map.put("categories", categories);

        //类别
        return "goods/add";
    }

    @GetMapping("/edit")
    public String edit(Integer id, Map<String, Object> map) {
        if (id == null) {
            map.put("error", "未提供要修改的商品号");
            return "goods/edit";
        }

        List<Brand> brands = brandService.findAll(null, new PaginateInfo(1, Integer.MAX_VALUE));
        map.put("brands", brands);

        List<Category> categories = categoryService.findAll();
        map.put("categories", categories);

        Goods goods = service.findById(id);
        if (goods == null) {
            map.put("error", "您要修改的商品不存在");
            return "goods/edit";
        }
        map.put("goods", goods);
        map.put("upload_server_url", Global.UPLOAD_SERVER_URL);

        return "goods/edit";
    }
}
