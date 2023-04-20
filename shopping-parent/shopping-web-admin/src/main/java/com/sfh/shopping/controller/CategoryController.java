package com.sfh.shopping.controller;

import com.sfh.shopping.model.Category;
import com.sfh.shopping.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @RequestMapping ("/list")
    public String list(){
        return "category/list";
    }

    @RequestMapping("/add")
    public String add(Integer parentId, Map<String, Object> map) {
        if (parentId != null) {
            Category category = service.findById(parentId);
            map.put("parentId", parentId);
            map.put("parentName", category.getName());
        }
        return "category/add";
    }

    @RequestMapping("/edit")
    public String edit(Integer id, Integer parentId, Map<String, Object> map) {
        String error;
        if (id == null) {
            error = "要修改的类别编号不可为空";
            map.put("error", error);
            return "category/edit";
        }

        Category category = service.findById(id);
        if (category == null) {
            error = "要修改的类别信息不存在";
            map.put("error", error);
            return "category/edit";
        }
        return "category/edit";

    }
}
