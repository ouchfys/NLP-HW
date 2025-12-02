package com.example.vo;

import com.example.entity.Order;
import lombok.Data;

@Data
public class OrderVO extends Order {

    private Integer unreadNum;

    private Integer commentId;
}
