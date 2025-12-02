package com.example.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.example.entity.User;
import exception.ServerException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class UserContext {

    /**
     * 从token获取当前用户信息
     * @return
     */
    public static User getCurrUser(){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null) {
            throw new RuntimeException("服务异常");
        }
        HttpServletRequest request = (HttpServletRequest) sra.getRequest();
        String token = request.getHeader("Authorization");
        if (ObjectUtil.isEmpty(token)){
            return null;
        }
        try {
            JWT jwt = JWTUtil.parseToken(token);
            JSONObject payloads = jwt.getPayloads();
            User user = payloads.toBean(User.class);
            return user;
        }catch (Exception e){
            throw new ServerException("用户信息错误请重新登录");
        }
    }
}
