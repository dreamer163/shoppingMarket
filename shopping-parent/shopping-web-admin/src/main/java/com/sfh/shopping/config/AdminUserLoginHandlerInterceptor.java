package com.sfh.shopping.config;

import com.sfh.shopping.model.User;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminUserLoginHandlerInterceptor implements HandlerInterceptor {
    public static final String CURRENT_LOGIN_USER_KEY = "user";

    /*后端拦截*/
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute(CURRENT_LOGIN_USER_KEY);

        if (user == null) {
//            String uri = request.getRequestURI();//地址
//            request.getSession().setAttribute("last_request_uri", uri);
            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        } else {
            return true;
        }
    }
}
