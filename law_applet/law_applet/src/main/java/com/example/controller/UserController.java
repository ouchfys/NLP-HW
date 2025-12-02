package com.example.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.entity.Order;
import com.example.entity.User;
import com.example.service.OrderService;
import com.example.service.UserService;
import com.example.util.*;
import com.example.vo.UserVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;
    @PostMapping("/login")
    public R login(@RequestBody @Validated  User user){
        return R.ok(userService.login(user));
    }


    @PostMapping("/register")
    public R register(@RequestBody @Validated  User user){
        return R.ok(userService.register(user));
    }


    @PostMapping("/remove")
    public R remove(@RequestParam("ids") String ids){
        return R.ok(userService.removeByIds(Arrays.asList(ids.split(","))));
    }

    @PostMapping("/update")
    public R update(@RequestBody User user){
        return R.ok(userService.updateById(user));
    }

    @PostMapping("/info")
    public R info(){
        User currUser = UserContext.getCurrUser();
        User user = userService.getById(currUser.getId());
        long count = orderService.count(Wrappers.<Order>lambdaQuery()
                .eq(Order::getLawyerUserId, user.getId())
                .in(Order::getStatus, Arrays.asList(2, 3))
        );
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        userVO.setAccessNum(count);
        return R.ok(userVO);
    }

    // 用户信息分页列表/律师推荐",notes = "可根据roleId查询和account、userName模糊查询")
    @GetMapping("/page")
    public R<IPage<UserVO>> page(User user, Query query){
        IPage<UserVO> page = Condition.getPage(query);
        List<UserVO> userList = userService.userPage(page, user);
        page.setRecords(userList);
        return R.ok(page);
    }
}
