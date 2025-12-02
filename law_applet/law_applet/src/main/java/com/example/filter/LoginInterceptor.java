package com.example.filter;

import cn.hutool.jwt.JWTUtil;
import com.example.util.CustomAssert;
import exception.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private String key = "token_####";

    // 在请求处理之前进行调用（Controller方法调用之前）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         //返回true才会继续向下执行，返回false取消当前请求
        String token = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(token)) {
            CustomAssert.alert("用户未授权");
            return false;
        }
        try {
            // 验证token是否有效等操作
            boolean verify = JWTUtil.verify(token, key.getBytes());
            return verify;
        }catch (Exception e){
            throw new AuthException("无效token");
        }

    }
}
