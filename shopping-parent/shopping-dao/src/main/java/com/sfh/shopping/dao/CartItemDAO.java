package com.sfh.shopping.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.sfh.shopping.model.CartItem;

import java.util.List;

@Mapper
public interface CartItemDAO {
    int insert(CartItem item);

    int update(CartItem item);

    int updateAmount(CartItem item);

    int deleteById(Integer id);

    int deleteByIds(@Param("ids") Integer[] ids);

    List<CartItem> findAllByUserId(Integer userId);

    List<CartItem> findByIds(@Param("ids") Integer[] ids);

    CartItem findByUserIdAndGoodId(@Param("userId") Integer userId,
                                   @Param("goodId") Integer goodId);
}
