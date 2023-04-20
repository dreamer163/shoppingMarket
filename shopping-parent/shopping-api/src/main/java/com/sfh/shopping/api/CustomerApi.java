package com.sfh.shopping.api;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.Customer;
import com.sfh.shopping.model.CustomerAddress;
import com.sfh.shopping.model.CustomerSearchBean;
import com.sfh.shopping.service.CustomerAddressService;
import com.sfh.shopping.service.CustomerService;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/api/customers", produces = "application/json;charset=utf-8")

public class CustomerApi {
    private final CustomerService service;

    private CustomerAddressService customerAddressService;

    @Autowired
    public void setCustomerAddressService(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    public CustomerApi(CustomerService service) {
        this.service = service;
    }

    /**
     * 获取前台用户地址
     */
    @GetMapping("/address")
    public ResponseEntity<Map<String, Object>> address(Integer addressId) {
        CustomerAddress ua = customerAddressService.findById(addressId);
        if (ua != null) {
            return ResponseEntity.ok(Map.of("address", ua));
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Object>> findByUsername(@PathVariable String username) {
        Customer customer = service.findByUsername(username);
        if (customer == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(Map.of("customer", customer));
        }
    }

    //查询
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(CustomerSearchBean csb,
                                                       @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {

        PaginateInfo pi = new PaginateInfo(pageNo, pageSize);
        List<Customer> customers = service.findAll(csb, pi);

        //总数和行数列表
        return ResponseEntity.ok(Map.of("total", pi.getTotal(), "rows", customers));
    }

    //添加
    @PostMapping
    public ResponseEntity<Map<String, Object>> add(Customer customer, MultipartFile pic) throws IOException {
        //校验
        if (!StringUtils.hasText(customer.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("error", "会员用户名不可为空！！"));
        }
        File target = null;
        if (pic != null) {
            target = new File(Global.UPLOAD_DIRECTORY, "customer/");
            if (!target.exists()) {
                boolean b = target.mkdirs();
                if (!b) {
                    return ResponseEntity.internalServerError().body(Map.of("error", "服务器创建会员用户图片目录失败"));
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
                customer.setPicUrl("customer/" + newName);
            }

        }

        boolean success = service.add(customer);
        if (success) {
            return ResponseEntity.ok(Map.of("customer", customer, "success", true));
        } else {
            return ResponseEntity.status(550).body(Map.of("error", "添加会员用户失败！！"));
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
    public ResponseEntity<Map<String, Object>> edit(Customer customer,MultipartFile pic) throws IOException {
        //校验
        if (!StringUtils.hasText(customer.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("error", "会员用户名不可为空！！"));
        }
        File target = null;
        if (pic != null) {
            target = new File(Global.UPLOAD_DIRECTORY, "customer/");
            if (!target.exists()) {
                boolean b = target.mkdirs();
                if (!b) {
                    return ResponseEntity.internalServerError().body(Map.of("error", "服务器创建会员用户图片目录失败"));
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
                customer.setPicUrl("customer/" + newName);
            }else {
                target = null;
            }

        }

        boolean success = service.update(customer);
        if (success) {
            if (target != null) {
                pic.transferTo(target);
            }
            return ResponseEntity.ok(Map.of("customer", customer,"success",true));
        } else {
            return ResponseEntity.status(560).body(Map.of("error", "会员用户修改失败！"));
        }

    }
}
