package com.sfh.shopping.controller;

import com.sfh.shopping.common.Global;
import com.sfh.shopping.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@Controller
public class IndexController {

    @GetMapping("/index")
    public String list(HttpServletRequest req,Map<String,Object> map){
        User user = (User) req.getSession().getAttribute("user");
        map.put("user",user);

        String serverUrl =Global.UPLOAD_SERVER_URL;
        map.put("serverUrl",serverUrl);

        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }

}
