package com.example.config;

import com.example.util.R;
import exception.AuthException;
import exception.ServerException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理自定义异常
    @ExceptionHandler(ServerException.class)
    public R serverException(Exception e){
        return R.fail(400,e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public R authException(Exception e){
        return R.fail(401,e.getMessage());
    }

}
