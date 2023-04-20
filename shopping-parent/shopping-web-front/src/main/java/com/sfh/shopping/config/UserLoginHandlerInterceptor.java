package com.sfh.shopping.config;

import com.sfh.shopping.model.Customer;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 前台用户认证拦截器
 */
public class UserLoginHandlerInterceptor implements HandlerInterceptor {
    public static final String CURRENT_LOGIN_USER_KEY = "user";

    /*前置拦截*/
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        Customer user = (Customer) session.getAttribute(CURRENT_LOGIN_USER_KEY);
        if (user == null) {
            String uri = request.getRequestURI();//地址
            String queryString = request.getQueryString();//问号后面的参数
            if (StringUtils.hasText(queryString)) {
                uri += "?" + queryString;
            }
            request.getSession().setAttribute("last_request_uri", uri);

            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        } else {
            return true;
        }
    }
}
