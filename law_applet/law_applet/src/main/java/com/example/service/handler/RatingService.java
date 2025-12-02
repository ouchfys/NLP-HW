package com.example.service.handler;

import com.example.entity.Comment;
import com.example.entity.Order;
import com.example.mapper.CommentMapper;
import com.example.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CommentMapper commentMapper;

    // 获取用户-律师评分矩阵
    public Map<Integer, Map<Integer, Integer>> getUserLawyerRatingMatrix() {
        // 查询所有订单
        List<Order> orders = orderMapper.selectList(null);
        if (orders == null || orders.isEmpty()) {
            return Collections.emptyMap(); // 如果没有订单，返回空Map
        }

        // 查询所有评论
        List<Comment> comments = commentMapper.selectList(null);
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyMap(); // 如果没有评论，返回空Map
        }

        // 订单ID -> 评价
        Map<Integer, Comment> orderCommentMap = comments.stream()
                .collect(Collectors.toMap(Comment::getOrderId, comment -> comment));

        // 用户ID -> (律师ID -> 评分)
        Map<Integer, Map<Integer, Integer>> ratingMatrix = new HashMap<>();

        for (Order order : orders) {
            Comment comment = orderCommentMap.get(order.getId());
            if (comment != null && comment.getStatus() == 1) { // 只使用审核通过的评价
                ratingMatrix
                        .computeIfAbsent(order.getUserId(), k -> new HashMap<>())
                        .put(order.getLawyerUserId(), comment.getStar());
            }
        }

        return ratingMatrix;
    }
}