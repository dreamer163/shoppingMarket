package com.sfh.shopping.api;

import com.sfh.shopping.model.Category;
import com.sfh.shopping.service.CategoryService;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/categories", produces = "application/json;charset=utf-8")
public class CategoryApi {
    private final CategoryService service;

    public CategoryApi(CategoryService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> list(){
        List<Category> categoryList = service.findAll();
        return ResponseEntity.ok(Map.of("rows", categoryList, "total", categoryList.size()));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(Category category, Integer parentId) throws IOException {
        //参数校验
        if (!StringUtils.hasText(category.getName())) {
            return ResponseEntity.badRequest().body(Map.of("error", "类别名称不可为空"));
        }
        if (parentId != null) {
            Category parent = new Category();
            parent.setId(parentId);
            category.setParent(parent);
        }

        try {
            boolean success = service.save(category);
            if (success) {
                return ResponseEntity.ok(Map.of("entity", category));
            } else {
                return ResponseEntity.accepted().body(Map.of("error", "保存商品类别失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "保存商品类别异常"));
        }
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> update(Category category, Integer parentId) {
        //参数校验
        if (!StringUtils.hasText(category.getName())) {
            return ResponseEntity.badRequest().body(Map.of("error", "类别名称不可为空"));
        }

        Category old = service.findById(category.getId());//数据库中查询原始记录
        if (old == null) {
            return ResponseEntity.notFound().build();
        }

        if (parentId != null) {
            Category parent = new Category();
            parent.setId(parentId);
            category.setParent(parent);
        }

        //复制属性
        BeanUtils.copyProperties(category, old);

        try {
            boolean success = service.update(category);
            if (success) {
                return ResponseEntity.ok(Map.of("entity", category));
            } else {
                return ResponseEntity.accepted().body(Map.of("error", "修改商品类别失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "修改商品类别异常"));
        }
    }


    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteByIds(Integer[] ids) {
        if (ids != null && ids.length > 0) {
            //校验
            for (Integer id : ids) {
                Category category = service.findById(id);
                if (category != null && category.getChildren() != null && category.getChildren().size() > 0) {//有子元素
                    return ResponseEntity.badRequest().body(Map.of("error", "要删除的类别，尚存在子类别，无法删除"));
                }
            }

            int rows = service.deleteByIds(ids);
            return ResponseEntity.ok(Map.of("rows", rows));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "未提交要删除的记录编号"));
        }
    }
}
