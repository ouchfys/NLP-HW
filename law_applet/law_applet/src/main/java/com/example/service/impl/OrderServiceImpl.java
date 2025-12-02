package com.example.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.entity.Order;
import com.example.entity.User;
import com.example.mapper.OrderMapper;
import com.example.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.service.UserService;
import com.example.util.CustomAssert;
import com.example.util.UserContext;
import com.example.vo.OrderVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private UserService userService;
    @Override
    public Order addUpdate(Order order) {
        if (ObjectUtils.isEmpty(order.getId())){
            if (ObjectUtils.isEmpty(order.getLawyerUserId())){
                CustomAssert.alert("请选择律师");
            }
            User lawyer = userService.getById(order.getLawyerUserId());
            order.setOrderNo(IdUtil.getSnowflakeNextIdStr());
            order.setUserId(UserContext.getCurrUser().getId());
            order.setPrice(lawyer.getConsultPrice());
            save(order);
            return order;
        }
        if (order.getStatus() == 2){
            order.setPayTime(new Date());
        }
        if (order.getStatus() == 3){
            order.setFinishTime(new Date());
        }
        updateById(order);
        return order;
    }

    @Override
    public List<OrderVO> orderPage(IPage<OrderVO> page, Order order) {
        return baseMapper.orderPage(page, order);
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        return list(Wrappers.<Order>lambdaQuery().eq(Order::getUserId,userId).ne(Order::getStatus,1));
    }

    @Override
    public List<Order> getOrdersByLawyerId(Integer lawyerId) {
        return list(Wrappers.<Order>lambdaQuery().eq(Order::getLawyerUserId,lawyerId).ne(Order::getStatus,1));
    }
}
