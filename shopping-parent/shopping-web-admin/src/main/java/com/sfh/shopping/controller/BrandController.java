package com.sfh.shopping.controller;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.Brand;
import com.sfh.shopping.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/brand")
public class BrandController {
    private final BrandService service;

    public BrandController(BrandService service) {
        this.service = service;
    }

    //查询
    @GetMapping("/list")
    public String list(Map<String, Object> map) {
        map.put("upload_server_url", Global.UPLOAD_SERVER_URL);
        return "brand/list";
    }

    //新增品牌
    @GetMapping("/add")
    public String add() {
        return "brand/add";
    }

    @GetMapping("/edit")
    public String edit(Integer id, Map<String, Object> map) {
        if (id == null) {
            map.put("error", "未提供要修改的品牌号");
            return "brand/edit";
        }
        Brand brand = service.findById(id);
        if (brand == null) {
            map.put("error", "您要修改的品牌不存在");
            return "brand/edit";
        }
        map.put("brand", brand);
        map.put("upload_server_url", Global.UPLOAD_SERVER_URL);

        return "brand/edit";
    }

}
