package com.sfh.shopping.api;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.User;

import com.sfh.shopping.model.UserSearchBean;
import com.sfh.shopping.service.UserService;
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
@RequestMapping(value = "/api/users", produces = "application/json;charset=utf-8")

public class UserApi {
    private final UserService service;

    public UserApi(UserService service) {
        this.service = service;
    }


    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Object>> findByUsername(@PathVariable String username) {
        User user = service.findByUsername(username);
        if (user == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(Map.of("user", user));
        }
    }

    //查询
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(UserSearchBean usb,
                                                       @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {

        PaginateInfo pi = new PaginateInfo(pageNo, pageSize);
        List<User> users = service.findAll(usb, pi);

        //总数和行数列表
        return ResponseEntity.ok(Map.of("total", pi.getTotal(), "rows", users));
    }

    //添加
    @PostMapping
    public ResponseEntity<Map<String, Object>> add(User user, MultipartFile pic) throws IOException {
        //校验
        if (!StringUtils.hasText(user.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("error", "用户名不可为空！！"));
        }
        File target = null;
        if (pic != null) {
            target = new File(Global.UPLOAD_DIRECTORY, "user/");
            if (!target.exists()) {
                boolean b = target.mkdirs();
                if (!b) {
                    return ResponseEntity.internalServerError().body(Map.of("error", "服务器创建后台管理员图片目录失败"));
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
                user.setPicUrl("user/" + newName);
            }

        }

        boolean success = service.add(user);
        if (success) {
            return ResponseEntity.ok(Map.of("user", user, "success", true));
        } else {
            return ResponseEntity.status(550).body(Map.of("error", "添加用户失败！！"));
        }
    }

    //删除
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteByIds(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "要删除的后台用户的编号为空！"));
        } else {
            int rows = service.deleteByIds(ids);
            return ResponseEntity.ok(Map.of("rows", rows));
        }
    }

    //修改
    @PutMapping
    public ResponseEntity<Map<String, Object>> edit(User user,MultipartFile pic) throws IOException {
        //校验
        if (!StringUtils.hasText(user.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("error", "用户名不可为空！！"));
        }
        File target = null;
        if (pic != null) {
            target = new File(Global.UPLOAD_DIRECTORY, "user/");
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
                user.setPicUrl("user/" + newName);
            }else {
                target = null;
            }
        }

        boolean success = service.update(user);
        if (success) {
            if (target != null) {
                pic.transferTo(target);
            }
            return ResponseEntity.ok(Map.of("user", user,"success",true));
        } else {
            return ResponseEntity.status(560).body(Map.of("error", "后台用户修改失败！"));
        }

    }
}
