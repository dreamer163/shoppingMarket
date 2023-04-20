package com.sfh.shopping.api;

import com.sfh.shopping.model.Order;
import com.sfh.shopping.model.OrderSearchBean;
import com.sfh.shopping.service.OrderService;
import com.sfh.shopping.util.IdGenerator;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/orders", produces = "application/json;charset=utf-8")
public class OrderApi {
    private final OrderService orderService;

    public OrderApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> list(OrderSearchBean osb,
                                                    @RequestParam(defaultValue = "1") Integer pageNo,
                                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        PaginateInfo pi = new PaginateInfo(pageNo, pageSize);
        List<Order> orders = orderService.findAll(osb, pi);

        return ResponseEntity.ok(Map.of("rows", orders, "total", pi.getTotal()));
    }


    /**
     * 创建订单
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> add(Order order, Integer[] cartItemIds) {
        order.setOrderId(IdGenerator.create());
        boolean success = orderService.saveWithCartItems(order, cartItemIds);

        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "orderId", order.getId()));
        } else {
            return ResponseEntity.status(1002).body(Map.of("error", "保存失败"));
        }
    }


    /**
     * 修改订单的支付状态，支付方式，地址
     */
    @PatchMapping
    public ResponseEntity<Map<String, Object>> patch(Order order) {

        boolean success = orderService.update(order);

        if (success) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.status(1002).body(Map.of("error", "修改失败"));
        }
    }

    /**
     * 发货
     */
    @PatchMapping("/send")
    public ResponseEntity<Map<String, Object>> send(Integer[] ids) {
        int rows = orderService.updateStateByIds(ids, "2");//2代表已发货

        return ResponseEntity.ok(Map.of("rows", rows));
    }

    /**
     * 确认收货
     */
    @PatchMapping("/confirm")
    public ResponseEntity<Map<String, Object>> confirm(Integer orderId) {
        Integer[] ids = new Integer[]{orderId};
        int rows = orderService.updateStateByIds(ids, "3");//3代表已确认收货

        return ResponseEntity.ok(Map.of("rows", rows, "success", true));
    }
}
