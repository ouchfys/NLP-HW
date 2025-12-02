package com.example.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.UserVO;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
public interface UserMapper extends BaseMapper<User> {


    List<UserVO> userPage(IPage<UserVO> page, User user);
}
