package com.sfh.shopping.controller;

import com.sfh.shopping.config.UserLoginHandlerInterceptor;
//import com.sfh.shopping.model.AddressPart;
import com.sfh.shopping.model.AddressPart;
import com.sfh.shopping.model.CustomerAddress;
import com.sfh.shopping.model.Order;
import com.sfh.shopping.model.Customer;
//import com.sfh.shopping.model.UserAddress;
import com.sfh.shopping.service.CategoryService;
import com.sfh.shopping.service.CustomerAddressService;
import com.sfh.shopping.service.OrderService;
//import com.sfh.shopping.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    private CustomerAddressService customerAddressService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setCustomerAddressService(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    /**
     * 跳转到结算页
     */
    @GetMapping("/checkout")
    public String add(Integer orderId, Map<String, Object> map, HttpSession session) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            map.put("error", "要结算的订单不存在");
            return "checkout";
        }

        Customer user = (Customer) session.getAttribute(UserLoginHandlerInterceptor.CURRENT_LOGIN_USER_KEY);
        List<CustomerAddress> uas = customerAddressService.findByUserId(user.getId());
        map.put("uas", uas);
        map.put("order", order);
        map.put("roots", categoryService.findAllRoots());
        //查出所有省份
        List<AddressPart> ps = customerAddressService.findAllProvinces();
        map.put("ps", ps);

        return "checkout";
    }


    //订单结束页
    @GetMapping("/complete")
    public String complete( Map<String, Object> map) {

        map.put("roots", categoryService.findAllRoots());
        return "order_complete";
    }
}
