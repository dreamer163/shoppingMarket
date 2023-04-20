package com.sfh.shopping.controller;

import com.sfh.shopping.config.UserLoginHandlerInterceptor;
import com.sfh.shopping.model.Order;
import com.sfh.shopping.model.OrderSearchBean;
import com.sfh.shopping.model.Customer;
import com.sfh.shopping.service.OrderService;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/personal")
public class PersonalController {
    private final OrderService orderService;

    public PersonalController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 跳转到个人中心页
     */


    @GetMapping("/index")
    public String account(HttpSession session, Map<String, Object> map,
                          @RequestParam(defaultValue = "1") Integer pageNo,
                          @RequestParam(defaultValue = "20") Integer pageSize) {
        Customer user = (Customer) session.getAttribute(UserLoginHandlerInterceptor.CURRENT_LOGIN_USER_KEY);
        OrderSearchBean osb = new OrderSearchBean();
        osb.setUserId(user.getId());
        PaginateInfo pi = new PaginateInfo(pageNo, pageSize);
        List<Order> orders = orderService.findAll(osb, pi);

        map.put("orders", orders);

        return "account";
    }
}
