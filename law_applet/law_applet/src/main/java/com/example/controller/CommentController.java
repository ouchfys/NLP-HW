package com.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.entity.Comment;
import com.example.service.CommentService;
import com.example.util.Condition;
import com.example.util.Query;
import com.example.util.R;
import com.example.vo.CommentVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/save")
    public R<Boolean> save(@RequestBody Comment comment){
        comment.setStatus(0);
        Comment one = commentService.getOne(Wrappers.<Comment>lambdaQuery().eq(Comment::getOrderId, comment.getOrderId()).last("limit 1"));
        if (one != null){
            comment.setId(one.getId());
        }
        return R.ok(commentService.saveOrUpdate(comment));
    }


    @GetMapping("/page")
    public R<IPage<CommentVO>> page(Query query, Comment comment){
        IPage<CommentVO> page = Condition.getPage(query);
        page = commentService.commentPage(page, comment);
        return R.ok(page);
    }

    @PostMapping("/update")
    public R<Boolean> update(@RequestBody Comment comment){
        return R.ok(commentService.updateById(comment));
    }

    @PostMapping("/delete")
    public R<Boolean> delete(@RequestBody Comment comment){
        return R.ok(commentService.removeById(comment.getId()));
    }
}
