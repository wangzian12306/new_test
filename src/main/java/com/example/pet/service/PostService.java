package com.example.pet.service;

import com.example.pet.pojo.Post;
import java.util.List;
import java.util.Map;

public interface PostService {
    // 帖子查询功能
    List<Map<String, Object>> getAllPostTitlesAndIds();
    Post getPostById(Integer postId);

    // 帖子管理功能
    int createPost(Post post);
    int updateReviewStatus(Integer postId, Integer status);
    int incrementLikes(Integer postId);
    int deletePost(Integer postId);
}