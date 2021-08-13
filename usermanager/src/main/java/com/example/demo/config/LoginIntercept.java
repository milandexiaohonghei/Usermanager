package com.example.demo.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginIntercept implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 判断 session 是否有值
        HttpSession session = request.getSession(false);
        if (session != null &&
                session.getAttribute(AppFinal.USERINFO_SESSIONKEY) != null) {
            // 用户已经登录
            return true;
        }
        return false;
    }
}
