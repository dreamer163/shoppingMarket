package com.sfh.shopping.controller;

//import com.sfh.shopping.config.UserLoginHandlerInterceptor;
import com.sfh.shopping.config.UserLoginHandlerInterceptor;
import com.sfh.shopping.model.Category;
import com.sfh.shopping.model.Customer;
import com.sfh.shopping.service.CategoryService;
import com.sfh.shopping.service.CustomerService;
import com.sfh.shopping.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/user")
public class UserController {
    private final CustomerService userService;

    public UserController(CustomerService userService) {
        this.userService = userService;
    }

    private CategoryService categoryService;
    private GoodsService goodService;

    @Autowired
    public void setService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setGoodService(GoodsService goodService) {
        this.goodService = goodService;
    }

    /**
     * 跳转到登录页
     */
    @GetMapping("/login")
    public String login(Map<String,Object> map) {

        List<Category> list = categoryService.findAllRoots();
        map.put("roots", list);//一级分类

        return "login";
    }

    @PostMapping("/login")
    public String login(Customer user, Map<String, Object> map,
                        HttpServletRequest request, HttpSession session) {
        Customer dbUser = userService.findByUsername(user.getUsername());
        if (dbUser == null) {
            map.put("error", "用户不存在");
            return "login";
        }
        if (Boolean.FALSE.equals(dbUser.getEnabled())) {
            map.put("error", "该用户已冻结");
            return "login";
        }

        String password = user.getPassword() + "{" + user.getUsername() + "}";
        String encrypt = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (encrypt.equals(dbUser.getPassword())) {
            session.setAttribute(UserLoginHandlerInterceptor.CURRENT_LOGIN_USER_KEY, dbUser);
//            String uri = (String) session.getAttribute("last_request_uri");
//            if (!StringUtils.hasText(uri)) {
//                uri = "/index";
//            }
            return "redirect:"+"/index" ;
        } else {
            map.put("error", "密码不正确");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute(UserLoginHandlerInterceptor.CURRENT_LOGIN_USER_KEY);

        return "redirect:"+ "/user/login";
    }
}
