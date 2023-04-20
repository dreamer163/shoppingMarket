package com.sfh.shopping.controller;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.config.UserLoginHandlerInterceptor;
import com.sfh.shopping.model.CartItem;
import com.sfh.shopping.model.Customer;
import com.sfh.shopping.model.Goods;
import com.sfh.shopping.service.CartService;
import com.sfh.shopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 添加一个购物车项
     */
    @PostMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addItem(Integer goodId, Integer amount, HttpSession session) {

        Customer user = (Customer) session.getAttribute(UserLoginHandlerInterceptor.CURRENT_LOGIN_USER_KEY);
        //从session域中取出用户
        CartItem dbItem = cartService.findByUserIdAndGoodId(user.getId(), goodId);

        boolean success = false;

        if (dbItem == null) {
            CartItem item = new CartItem();
            item.setUserId(user.getId());//商品编号
            Goods good = new Goods();
            good.setId(goodId);
            item.setGood(good);
            item.setAmount(amount);//商品数量
            success = cartService.addCartItem(item);
        } else {
            dbItem.setAmount(dbItem.getAmount() + amount);
            success = cartService.updateCartItem(dbItem);
        }

        if (success) {
            return ResponseEntity.ok().body(Map.of("success", true));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/list")
    public String list(Map<String, Object> map, HttpSession session) {
        Customer user = (Customer) session.getAttribute(UserLoginHandlerInterceptor.CURRENT_LOGIN_USER_KEY);
        List<CartItem> cartItems = cartService.findAllByUserId(user.getId());
        map.put("cartItems", cartItems);
        map.put("roots", categoryService.findAllRoots());
        map.put("serverUrl", Global.UPLOAD_SERVER_URL);
        return "cart";
    }
    @PostMapping(value = "/edit", produces = "application/json;charset=utf-8")

    @ResponseBody
    public ResponseEntity<Map<String, Object>> edit(Integer goodId, Integer amount, HttpSession session) {
        CartItem cartItem = new CartItem();
        Goods good = new Goods();
        good.setId(goodId);
        cartItem.setGood(good);
        cartItem.setAmount(amount);

        if (amount < 1 || amount > 99) {
            return ResponseEntity.badRequest().body(Map.of("error", "数量必须大于0小于99"));
        }

        Customer user = (Customer) session.getAttribute(UserLoginHandlerInterceptor.CURRENT_LOGIN_USER_KEY);
        cartItem.setUserId(user.getId());

        boolean success = cartService.updateCartItemCount(cartItem);
        if (success) {
            return ResponseEntity.ok().body(Map.of("success", true));
        } else {
            return ResponseEntity.status(1000).body(Map.of("error", "修改失败"));
        }
    }

    @PostMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> delete(Integer id) {
        boolean success = cartService.deleteCartItemById(id);
        if (success) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.status(1002).body(Map.of("error", "删除失败"));
        }
    }
}
