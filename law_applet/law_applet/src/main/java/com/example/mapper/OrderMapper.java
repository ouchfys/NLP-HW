package com.example.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.OrderVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<OrderVO> orderPage(IPage<OrderVO> page, Order order);
}
