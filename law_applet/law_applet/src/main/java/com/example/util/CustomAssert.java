package com.example.util;

import com.example.entity.User;
import exception.ServerException;
import org.springframework.util.ObjectUtils;

public class CustomAssert {

    public static void alert(String message){
        throw new ServerException(message);
    }

    public static void isNotEmpty(Object o, String message){
        if (ObjectUtils.isEmpty(o)){
            throw new ServerException(message);
        }
    }

    public static void isNull(Object o, String message) {
        if (!ObjectUtils.isEmpty(o)){
            throw new ServerException(message);
        }
    }
}
