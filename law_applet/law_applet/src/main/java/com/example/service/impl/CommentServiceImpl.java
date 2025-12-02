package com.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.example.service.CommentService;
import com.example.vo.CommentVO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2024-05-03
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Override
    public List<CommentVO> getByLawId(Integer id) {
        return this.baseMapper.getByLawId(id);
    }

    @Override
    public IPage<CommentVO> commentPage(IPage<CommentVO> page, Comment comment) {
        List<CommentVO> commentVOS = this.baseMapper.commentPage(page, comment);
        page.setRecords(commentVOS);
        return page;
    }

}
