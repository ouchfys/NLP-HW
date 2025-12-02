package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.OrderVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
public interface OrderService extends IService<Order> {

    Order addUpdate(Order order);

    List<OrderVO> orderPage(IPage<OrderVO> page, Order order);


    /**
     * 根据用户ID获取订单
     * @param userId 用户ID
     * @return 该用户的订单列表
     */
    List<Order> getOrdersByUserId(Integer userId);

    /**
     * 根据律师ID获取订单
     * @param lawyerId 律师ID
     * @return 该律师的订单列表
     */
    List<Order> getOrdersByLawyerId(Integer lawyerId);

}
