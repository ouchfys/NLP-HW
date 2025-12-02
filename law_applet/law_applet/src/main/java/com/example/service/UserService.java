package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.UserVO;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
public interface UserService extends IService<User> {

    String login(User user);

    Boolean register(User user);

    List<UserVO> userPage(IPage<UserVO> page, User user);
}
