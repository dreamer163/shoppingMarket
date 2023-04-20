package com.sfh.shopping.api;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.*;
import com.sfh.shopping.service.GoodsService;
import com.sfh.shopping.util.NavigatePaginateInfo;
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
@RequestMapping(value = "/api/goods", produces = "application/json;charset=utf-8")
public class GoodsApi {
    private final GoodsService service;

    public GoodsApi(GoodsService service) {
        this.service = service;
    }


    //查询商品
    @GetMapping
    public ResponseEntity<Map<String, Object>> list(GoodsSearchBean gsb, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        PaginateInfo pi = new PaginateInfo(pageNo, pageSize);
        gsb.setIsDelete(false);
        List<Goods> goods = service.findAll(gsb, pi);
        NavigatePaginateInfo npi = new NavigatePaginateInfo(pi,5);
        return ResponseEntity.ok(Map.of("success",true,"rows", goods, "total", pi.getTotal(),"npi",npi));
    }

    //添加
    @PostMapping
    public ResponseEntity<Map<String, Object>> add(GoodsFormBean goods, MultipartFile pic) throws IOException {
        //校验
        if (!StringUtils.hasText(goods.getName())) {
            return ResponseEntity.badRequest().body(Map.of("error", "商品名不可为空！！"));
        }
        if (!StringUtils.hasText(goods.getSpuNo())) {
            return ResponseEntity.badRequest().body(Map.of("error", "商品编号不可为空！！"));
        }
        File target = null;
        if (pic != null) {
            target = new File(Global.UPLOAD_DIRECTORY, "good/");
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
                goods.setPicUrl("good/" + newName);
            }
        }

        if (goods.getBrandId() != null) {
            Brand b = new Brand();
            b.setId(goods.getBrandId());
            goods.setBrand(b);
        }

        if (goods.getCategoryId() != null) {
            Category c = new Category();
            c.setId(goods.getCategoryId());
            goods.setCategory(c);
        }


        boolean success = service.add(goods);
        if (success) {
            if (target != null) {
                pic.transferTo(target);
            }
            return ResponseEntity.ok(Map.of("goods", goods, "success", true));
        } else {
            return ResponseEntity.status(550).body(Map.of("error", "添加商品信息失败！！"));
        }
    }

    //修改
    @PutMapping
    public ResponseEntity<Map<String, Object>> edit(GoodsFormBean goods, MultipartFile pic) throws IOException {
        //校验
        if (!StringUtils.hasText(goods.getName())) {
            return ResponseEntity.badRequest().body(Map.of("error", "商品名不可为空！！"));
        }
        if (!StringUtils.hasText(goods.getSpuNo())) {
            return ResponseEntity.badRequest().body(Map.of("error", "商品编号不可为空！！"));
        }
        File target = null;
        if (pic != null) {
            target = new File(Global.UPLOAD_DIRECTORY, "good/");
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
                goods.setPicUrl("good/" + newName);
            } else {
                target = null;
            }
        }

        if (goods.getBrandId() != null) {
            Brand b = new Brand();
            b.setId(goods.getBrandId());
            goods.setBrand(b);
        }

        if (goods.getCategoryId() != null) {
            Category c = new Category();
            c.setId(goods.getCategoryId());
            goods.setCategory(c);
        }


        boolean success = service.update(goods);
        if (success) {
            if (target != null) {
                pic.transferTo(target);
            }
            return ResponseEntity.ok(Map.of("goods", goods, "success", true));
        } else {
            return ResponseEntity.status(550).body(Map.of("error", "修改商品信息失败！！"));
        }
    }

    //
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteByIds(Integer ids[]) {
        if (ids == null || ids.length == 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "要删除的商品为空！"));
        } else {
            int rows = service.deleteByIds(ids);
            return ResponseEntity.ok(Map.of("rows", rows));
        }
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> takeOffAndOn(Integer ids[], String act) {
        int rows = 0;
        if (act.equals("takeOff")) {
            if (ids != null && ids.length > 0) {
                for (Integer id : ids) {
                    Goods goods = new Goods();
                    goods.setId(id);
                    goods.setTakeOff(true);
                    rows += service.update(goods) ? 1 : 0;
                }
            }
        } else if (act.equals("takeOn")) {
            if (ids != null && ids.length > 0) {
                for (Integer id : ids) {
                    Goods goods = new Goods();
                    goods.setId(id);
                    goods.setTakeOff(false);
                    rows += service.update(goods) ? 1 : 0;
                }
            }

        }
        return ResponseEntity.ok(Map.of("rows", rows));
    }
}