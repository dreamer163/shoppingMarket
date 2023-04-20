package com.sfh.shopping.service;

import com.sfh.shopping.model.Order;
import com.sfh.shopping.model.OrderSearchBean;
import com.sfh.shopping.util.PaginateInfo;

import java.util.List;

public interface OrderService {
    List<Order> findAll(OrderSearchBean osb, PaginateInfo pi);

    boolean save(Order order);

    boolean saveWithCartItems(Order order, Integer[] orderItemIds);

    Order findById(Integer id);

    /**
     * 修改订单
     */
    boolean update(Order order);

    /**
     * 修改订单状态
     */
    int updateStateByIds(Integer[] ids, String state);
}
