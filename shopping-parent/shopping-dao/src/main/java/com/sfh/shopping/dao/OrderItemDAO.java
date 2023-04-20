package com.sfh.shopping.dao;

import org.apache.ibatis.annotations.Mapper;
import com.sfh.shopping.model.OrderItem;

/**
*/
@Mapper
public interface OrderItemDAO {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

}
