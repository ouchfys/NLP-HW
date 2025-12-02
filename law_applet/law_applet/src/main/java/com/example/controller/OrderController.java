package com.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Order;
import com.example.entity.User;
import com.example.service.OrderService;
import com.example.util.Condition;
import com.example.util.Query;
import com.example.util.R;
import com.example.util.UserContext;
import com.example.vo.OrderVO;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/add-update")
    public R addUpdate(@RequestBody Order order){
        return R.ok(orderService.addUpdate(order));
    }

    @GetMapping("/page")
    public R<IPage<OrderVO>> page(Order order, Query query){
        User currUser = UserContext.getCurrUser();
        if (currUser.getRoleId() == 2){
            order.setLawyerUserId(currUser.getId());
        }else if (currUser.getRoleId() == 3){
            order.setUserId(currUser.getId());
        }
        IPage<OrderVO> page = Condition.getPage(query);
        List<OrderVO> orders = orderService.orderPage(page, order);
        page.setRecords(orders);
        return R.ok(page);
    }
}
