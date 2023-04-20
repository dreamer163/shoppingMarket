package com.sfh.shopping.controller;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.Brand;
import com.sfh.shopping.model.Goods;
import com.sfh.shopping.model.GoodsSearchBean;
import com.sfh.shopping.service.BrandService;
import com.sfh.shopping.service.CategoryService;
import com.sfh.shopping.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/good")
public class GoodController {
    private final GoodsService service;
    private CategoryService categoryService;
    private BrandService brandService;

    public GoodController(GoodsService service) {
        this.service = service;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @RequestMapping("/list")
    public String list(GoodsSearchBean gsb,
                       @RequestParam(defaultValue = "1") Integer pageNo,
                       @RequestParam(defaultValue = "12") Integer pageSize,
                       Map<String, Object> map) {

        map.put("brands", brandService.findAll(new Brand(), null));
        map.put("roots", categoryService.findAllRoots());
        map.put("serverUrl", Global.UPLOAD_SERVER_URL);
        return "good_list";
    }

    /**
     * 跳转到商品详情页
     */
    @RequestMapping("/detail")
    public String detail(Integer id, Map<String, Object> map) {
        Goods good = service.findById(id);
        if (good == null) {
            map.put("error", "要查看详情的商品不存在");
            return "good_detail";
        }

        map.put("serverUrl", Global.UPLOAD_SERVER_URL);
        map.put("good", good);

        map.put("brands", brandService.findAll(new Brand(), null));
        map.put("roots", categoryService.findAllRoots());
        return "good_detail";
    }
}
