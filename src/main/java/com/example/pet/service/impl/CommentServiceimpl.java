package com.example.pet.service.impl;

import com.example.pet.mapper.CommentMapper;
import com.example.pet.pojo.Comment;
import com.example.pet.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceimpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByPostId(Integer postId) {
        return commentMapper.findByPostId(postId);
    }

    @Override
    public int createComment(Comment comment) {
        // 内容校验
        if(comment.getContent()  == null || comment.getContent().isEmpty())  {
            throw new IllegalArgumentException("评论内容不能为空");
        }
        if(comment.getContent().length()  > 100) {
            throw new IllegalArgumentException("评论内容不能超过100字");
        }
        return commentMapper.createComment(comment);
    }

    @Override
    public int approveComment(Integer commentId) {
        return commentMapper.updateReviewStatus(commentId,  1); // 1表示审核通过
    }

    @Override
    public int rejectComment(Integer commentId) {
        return commentMapper.updateReviewStatus(commentId,  2); // 2表示审核拒绝
    }

    @Override
    public int batchApproveComments(List<Integer> commentIds) {
        if(commentIds == null || commentIds.isEmpty())  {
            throw new IllegalArgumentException("评论ID列表不能为空");
        }
        return commentMapper.batchUpdateStatus(commentIds,  1);
    }

    @Override
    public int deleteComment(Integer commentId) {
        return commentMapper.deleteComment(commentId);
    }
}