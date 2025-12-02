package com.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Message;
import com.example.entity.Order;
import com.example.mapper.MessageMapper;
import com.example.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.service.OrderService;
import com.example.util.CustomAssert;
import com.example.util.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 消息信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private OrderService orderService;
    @Override
    public Boolean sendMessage(Message message) {
        if (ObjectUtils.isEmpty(message.getReceiveUserId())){
            CustomAssert.alert("消息接受用户不能为空");
        }
        if (ObjectUtils.isEmpty(message.getOrderNo())){
            CustomAssert.alert("订单号不能为空");
        }
        Order order = orderService.getOne(Wrappers.<Order>lambdaQuery().eq(Order::getOrderNo,message.getOrderNo()).last("limit 1"));
        if (order.getStatus() == 3){
            CustomAssert.alert("订单已完成，无法发送消息");
        }
        message.setSendUserId(UserContext.getCurrUser().getId());
        message.setRole(UserContext.getCurrUser().getRoleId());
        return save(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Message> receiveMessage(Integer userId, String orderNo) {
        List<Message> messageList = this.list(Wrappers.<Message>lambdaQuery().eq(Message::getReceiveUserId, userId).eq(Message::getOrderNo, orderNo).eq(Message::getStatus,0));
        // 更新消息为已读
        updateMsgRead(messageList);
        return messageList;
    }

    @Override
    public IPage<Message> msgPage(IPage<Message>page,String orderNo) {
        IPage<Message> messagePage = this.page(page,Wrappers.<Message>lambdaQuery().eq(Message::getOrderNo, orderNo).orderByDesc(Message::getInsertTime));
        updateMsgRead(messagePage.getRecords());
        return messagePage;
    }

    void updateMsgRead(List<Message> messageList){
        List<Integer> ids = messageList.stream().map(Message::getId).collect(Collectors.toList());
        if (ids.isEmpty()){
            return;
        }
        update(Wrappers.<Message>lambdaUpdate().set(Message::getStatus, 1).in(Message::getId, ids));
    }
}
