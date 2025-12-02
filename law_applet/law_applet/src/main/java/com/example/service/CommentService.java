package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Comment;
import com.example.vo.CommentVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
public interface CommentService extends IService<Comment> {

    List<CommentVO> getByLawId(Integer id);

    IPage<CommentVO> commentPage(IPage<CommentVO> page, Comment comment);


}
