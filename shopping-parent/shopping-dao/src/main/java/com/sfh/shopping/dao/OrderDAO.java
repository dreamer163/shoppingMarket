package com.sfh.shopping.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import  com.sfh.shopping.model.Order;
import  com.sfh.shopping.model.OrderSearchBean;

import java.util.List;

@Mapper
public interface OrderDAO {
    List<Order> findAll(OrderSearchBean osb);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updateStateByIds(@Param("ids") Integer[] ids, @Param("state") String state);

}
