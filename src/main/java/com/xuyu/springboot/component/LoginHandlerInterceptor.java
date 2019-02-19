package com.xuyu.springboot.component;

import com.sun.istack.internal.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//这个拦截器进行登录检查，没有登录的用户就不能去检查
public class LoginHandlerInterceptor implements HandlerInterceptor {


    //目标方法执行之前
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         Object loginuser = request.getSession().getAttribute("user");
         if(loginuser!=null){
             //已经登录，放行
             return true;
         }else {
             //未登录，返回登录界面
             request.setAttribute("msg","没有权限请先登录");
             request.getRequestDispatcher("/index").forward(request,response);
             return false;
         }

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
