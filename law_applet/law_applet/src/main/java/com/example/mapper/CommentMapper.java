package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Comment;
import com.example.entity.Order;
import com.example.vo.CommentVO;
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
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentVO> getByLawId(Integer id);

    List<CommentVO> commentPage(IPage<CommentVO> page, Comment comment);
}
