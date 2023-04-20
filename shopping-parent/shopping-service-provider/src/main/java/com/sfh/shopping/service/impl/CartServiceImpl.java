package com.sfh.shopping.service.impl;

import com.sfh.shopping.dao.CartItemDAO;
import com.sfh.shopping.model.CartItem;
import com.sfh.shopping.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartItemDAO cartItemDAO;

    public CartServiceImpl(CartItemDAO cartItemDAO) {
        this.cartItemDAO = cartItemDAO;
    }

    @Override
    public boolean addCartItem(CartItem item) {
        return cartItemDAO.insert(item) > 0;
    }

    @Override
    public boolean updateCartItem(CartItem item) {
        return cartItemDAO.update(item) > 0;
    }

    @Override
    public boolean deleteCartItemById(Integer id) {
        return cartItemDAO.deleteById(id) > 0;
    }

    @Override
    public boolean updateCartItemCount(CartItem item) {
        return cartItemDAO.updateAmount(item) > 0;
    }

    @Override
    public CartItem findByUserIdAndGoodId(Integer userId, Integer goodId) {
        return cartItemDAO.findByUserIdAndGoodId(userId, goodId);
    }

    @Override
    public List<CartItem> findAllByUserId(Integer userId) {
        return cartItemDAO.findAllByUserId(userId);
    }
}
