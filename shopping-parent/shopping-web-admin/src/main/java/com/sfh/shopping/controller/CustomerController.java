package com.sfh.shopping.controller;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.Customer;
import com.sfh.shopping.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    public final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/list")

    public String list(Map<String,Object> map){
        map.put("upload_server_url", Global.UPLOAD_SERVER_URL);
        return "customer/list";
    }

    //添加
    @GetMapping("/add")
    public String add(){
        return "customer/add";
    }

    @GetMapping("/edit")
    public String edit(Integer id, Map<String,Object> map){
        Customer customer= service.findById(id);
        if (customer == null){
            map.put("error","修改的会员用户不存在");
        }else {
            map.put("customer",customer);
            map.put("upload_server_url", Global.UPLOAD_SERVER_URL);
        }
        return  "customer/edit";
    }
}
