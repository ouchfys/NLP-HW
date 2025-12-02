package com.example.vo;

import com.example.entity.Comment;
import com.example.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserVO extends User {

    private String roleName;

    private Long accessNum;

    private List<CommentVO> comments;
}
