package com.sfh.shopping.controller;
import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.User;
import com.sfh.shopping.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
@RequestMapping("/user")

public class UserController {

    public final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String list(Map<String,Object> map){
        map.put("upload_server_url", Global.UPLOAD_SERVER_URL);
        return "user/list";
    }

    //添加
    @GetMapping("/add")
    public String add(){
        return "user/add";
    }

    @GetMapping("/edit")
    public String edit(Integer id, Map<String,Object> map){
        User user= service.findById(id);
        if (user == null){
            map.put("error","修改的用户不存在");
        }else {
            map.put("user",user);
            map.put("upload_server_url", Global.UPLOAD_SERVER_URL);
        }
        return  "user/edit";
    }

    @RequestMapping("/login")
    public String login(){
        return "user/login";
    }

    @PostMapping("/login")
    public String login(User user, Map<String, Object> map,
                        HttpServletRequest request, HttpSession session) {
        User dbUser = service.findByUsername(user.getUsername());

        if (dbUser == null) {
            map.put("error", "用户不存在");
            return "user/login";
        }
//        if (Boolean.FALSE.equals(dbUser.getEnabled())) {
//            map.put("error", "该用户已冻结");
//            return "user/login";
//        }

        String password = user.getPassword() + "{" + user.getUsername() + "}";
        String encrypt = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (encrypt.equals(dbUser.getPassword())) {
            session.setAttribute("user", dbUser);
//
//            String uri = (String) session.getAttribute("last_request_uri");
//
//            if (!StringUtils.hasText(uri)) {
//                uri = "/index";
//            }
            return "redirect:" +  "/index";
        } else {
            map.put("error", "密码不正确");
            return "user/login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){

        session.removeAttribute("user");
        return "redirect:" +  "/user/login";
    }
}
