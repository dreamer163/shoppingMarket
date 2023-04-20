package com.sfh.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfh.shopping.dao.CartItemDAO;
import com.sfh.shopping.dao.OrderDAO;
import com.sfh.shopping.dao.OrderItemDAO;
import com.sfh.shopping.model.*;
import com.sfh.shopping.service.OrderService;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;

    private CartItemDAO cartItemDAO;

    public OrderServiceImpl(OrderDAO orderDAO, OrderItemDAO orderItemDAO) {
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
    }

    @Autowired
    public void setCartItemDAO(CartItemDAO cartItemDAO) {
        this.cartItemDAO = cartItemDAO;
    }

    @Override
    public List<Order> findAll(OrderSearchBean osb, PaginateInfo pi) {
        if (pi != null) {
            PageHelper.startPage(pi.getPageNo(), pi.getPageSize());
        }
        List<Order> result = orderDAO.findAll(osb);

        if (pi != null) {
            PageInfo<Order> page = new PageInfo<>(result);
            pi.setTotal((int) page.getTotal());
        }

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean save(Order order) {
        order.setGmtCreate(LocalDateTime.now());
        order.setGmtUpdate(LocalDateTime.now());
        order.setState("0");//未支付

        //数据库操作1
        int rows = orderDAO.insert(order);//保存订单

        //保存订单项
        for (OrderItem oi : order.getOrderItems()) {
            orderItemDAO.insert(oi);//数据库操作2
        }

        return rows > 0;
    }

    @Transactional
    @Override
    public boolean saveWithCartItems(Order order, Integer[] cartItemIds) {
        if (cartItemIds != null && cartItemIds.length > 0) {
            order.setTotal(BigDecimal.ZERO);
            List<CartItem> cartItemList = cartItemDAO.findByIds(cartItemIds);

            if (cartItemList.size() > 0) {
                for (CartItem ci : cartItemList) {
                    OrderItem oi = new OrderItem();
                    oi.setOrderId(order.getOrderId());
                    oi.setGood(ci.getGood());
                    oi.setGoodName(ci.getGood().getName());
                    oi.setGoodPic(ci.getGood().getPicUrl());
                    oi.setPrice(ci.getGood().getPrice());
                    oi.setAmount(ci.getAmount());
                    oi.setTotal(oi.getPrice().multiply(BigDecimal.valueOf(oi.getAmount())));

                    if (order.getOrderItems() == null) {
                        order.setOrderItems(new ArrayList<>());
                    }
                    order.getOrderItems().add(oi);

                    order.setTotal(order.getTotal().add(oi.getTotal()));
                }
            }
        }

        OrderService os = (OrderService) AopContext.currentProxy();
        //数据库操作1，创建订单
        boolean success = os.save(order);

        if (success) {
            //删除购物车项，数据库操作2
            if (cartItemIds != null && cartItemIds.length > 0) {
                cartItemDAO.deleteByIds(cartItemIds);
            }
        }
        return success;
    }

    @Override
    public Order findById(Integer id) {
        return orderDAO.selectByPrimaryKey(id);
    }

    @Override
    public boolean update(Order order) {
        return orderDAO.updateByPrimaryKeySelective(order) > 0;
    }

    @Override
    public int updateStateByIds(Integer[] ids, String state) {
        return orderDAO.updateStateByIds(ids, state);
    }
}
