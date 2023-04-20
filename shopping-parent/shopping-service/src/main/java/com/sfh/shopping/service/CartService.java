package com.sfh.shopping.service;

import com.sfh.shopping.model.CartItem;

import java.util.List;

public interface CartService {
    boolean addCartItem(CartItem item);

    boolean updateCartItem(CartItem item);

    boolean deleteCartItemById(Integer id);

    boolean updateCartItemCount(CartItem item);

    CartItem findByUserIdAndGoodId(Integer userId,Integer goodId);

    List<CartItem> findAllByUserId(Integer userId);
}
