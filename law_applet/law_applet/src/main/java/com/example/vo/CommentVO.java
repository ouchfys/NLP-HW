package com.example.vo;

import com.example.entity.Comment;
import lombok.Data;

@Data
public class CommentVO extends Comment {

    private String userName;

    private String orderNo;
}
