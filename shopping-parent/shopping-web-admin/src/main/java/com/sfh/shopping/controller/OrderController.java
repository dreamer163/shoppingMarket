package com.sfh.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/list")
    public String list(Integer state, Map<String, Object> map) {
        map.put("state", state);
        return "order/list";
    }
}
