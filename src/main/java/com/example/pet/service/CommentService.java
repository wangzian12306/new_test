package com.example.pet.service;

import com.example.pet.pojo.Comment;
import java.util.List;

public interface CommentService {
    // 查询功能
    List<Comment> getCommentsByPostId(Integer postId);

    // 评论发布
    int createComment(Comment comment);

    // 审核管理
    int approveComment(Integer commentId);
    int rejectComment(Integer commentId);
    int batchApproveComments(List<Integer> commentIds);
    int deleteComment(Integer commentId);

}