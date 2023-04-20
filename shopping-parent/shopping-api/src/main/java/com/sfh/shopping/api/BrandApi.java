package com.sfh.shopping.api;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.Brand;
import com.sfh.shopping.service.BrandService;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/brand", produces = "application/json;charset=utf-8")
public class BrandApi {
    private final BrandService service;

    public BrandApi(BrandService service) {
        this.service = service;
    }

    //查询全部
    @GetMapping
    public ResponseEntity<Map<String, Object>> list(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        PaginateInfo pi = new PaginateInfo(pageNo, pageSize);
        List<Brand> brandList = service.findAll(null, pi);
        return ResponseEntity.ok(Map.of("total", pi.getTotal(), "rows", brandList));
    }

    //增加品牌
    @PostMapping
    public ResponseEntity<Map<String, Object>> add(Brand brand, MultipartFile pic) throws IOException {
        File target = null;
        if (pic != null) {
            target = new File(Global.UPLOAD_DIRECTORY, "brand/");
            if (!target.exists()) {
                boolean b = target.mkdirs();
                if (!b) {
                    return ResponseEntity.internalServerError().body(Map.of("error", "服务器创建图片目录失败"));
                }
            }
            //源文件名
            String oldName = pic.getOriginalFilename();
            if (StringUtils.hasText(oldName)) {
                //文件扩展名的.
                int idx = oldName.lastIndexOf(".");
                //文件扩展名
                String ext = oldName.substring(idx);
                //新文件名
                String newName = UUID.randomUUID().toString() + ext;

                target = new File(target.getCanonicalPath() + "/" + newName);
                brand.setPicUrl("brand/" + newName);
            }
        }
        boolean success = service.save(brand);
        if (success) {
            if (target != null) {
                pic.transferTo(target);
            }
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.internalServerError().body(Map.of("error", "修改失败"));
        }
    }

    //修改品牌
    @PutMapping
    public ResponseEntity<Map<String, Object>> edit(Brand brand, MultipartFile pic) throws IOException {
        File target = null;
        if (pic != null) {
            target = new File(Global.UPLOAD_DIRECTORY, "brand/");
            if (!target.exists()) {
                boolean b = target.mkdirs();
                if (!b) {
                    return ResponseEntity.internalServerError().body(Map.of("error", "服务器创建图片目录失败"));
                }
            }
            //源文件名
            String oldName = pic.getOriginalFilename();
            if (StringUtils.hasText(oldName)) {
                //文件扩展名的.
                int idx = oldName.lastIndexOf(".");
                //文件扩展名
                String ext = oldName.substring(idx);
                //新文件名
                String newName = UUID.randomUUID().toString() + ext;

                target = new File(target.getCanonicalPath() + "/" + newName);
                brand.setPicUrl("brand/" + newName);
            } else {
                target = null;
            }

        }
        boolean success = service.update(brand);
        if (success) {
            if (target != null) {
                pic.transferTo(target);
            }
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.internalServerError().body(Map.of("error", "修改失败"));
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteByIds(Integer ids[]) {


        //todo 是否有使用此品牌的，有的话就不删除

        if (ids == null || ids.length == 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "要删除的品牌为空！"));
        } else {
            int rows = service.deleteByIds(ids);
            return ResponseEntity.ok(Map.of("rows", rows));
        }
    }
}