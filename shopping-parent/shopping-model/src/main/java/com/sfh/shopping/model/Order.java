package com.sfh.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order implements Serializable {
    private Integer id;

    private String orderId;//订单编号

    private Integer userId;

    private BigDecimal total;//订单总价

    private String payType;

    private String name;//收货人姓名

    private String email;

    private String phone;

    private String address;

    private String state;//订单状态

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;

    private String description;

    private List<OrderItem> orderItems;
}