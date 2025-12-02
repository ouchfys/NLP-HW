package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 消息信息表 服务类
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
public interface MessageService extends IService<Message> {

    Boolean sendMessage(Message message);

    List<Message> receiveMessage(Integer userId, String orderNo);

    IPage<Message> msgPage(IPage<Message> page, String orderNo);
}
