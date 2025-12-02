package com.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Message;
import com.example.service.MessageService;
import com.example.util.Condition;
import com.example.util.Query;
import com.example.util.R;
import com.example.util.UserContext;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 消息信息表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @PostMapping("/send-msg")
    public R<Boolean> sendMessage(@RequestBody Message message){
        return R.ok(messageService.sendMessage(message));
    }

    @PostMapping("/receive-msg/{orderNo}")
    public R<List<Message>> receiveMessage(@PathVariable("orderNo") String orderNo){
        Integer userId = UserContext.getCurrUser().getId();
        return R.ok(messageService.receiveMessage(userId, orderNo));
    }

    @PostMapping("/page")
    public R<IPage<Message>> page(Message message, Query query){
        IPage<Message> page = Condition.getPage(query);
        return R.ok(messageService.msgPage(page, message.getOrderNo()));
    }
}
