package com.example.pet.service.impl;

import com.example.pet.mapper.PostMapper;
import com.example.pet.pojo.Post;
import com.example.pet.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PostServiceimpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Map<String, Object>> getAllPostTitlesAndIds() {
        return postMapper.getAllPostTitlesAndIds();
    }

    @Override
    public Post getPostById(Integer postId) {
        return postMapper.getPostById(postId);
    }

    @Override
    public int createPost(Post post) {
        // 参数校验
        if(post.getTitle()  == null || post.getTitle().isEmpty())  {
            throw new IllegalArgumentException("帖子标题不能为空");
        }
        if(post.getContent()  == null || post.getContent().isEmpty())  {
            throw new IllegalArgumentException("帖子内容不能为空");
        }
        return postMapper.createPost(post);
    }

    @Override
    public int updateReviewStatus(Integer postId, Integer status) {
        // 状态有效性校验
        if(status < 0 || status > 2) {
            throw new IllegalArgumentException("审核状态值不合法");
        }
        return postMapper.updateReviewStatus(postId,  status);
    }

    @Override
    public int incrementLikes(Integer postId) {
        // 存在性校验
        Post post = postMapper.getPostById(postId);
        if(post == null) {
            throw new RuntimeException("指定帖子不存在");
        }
        return postMapper.incrementLikes(postId);
    }

    @Override
    @Transactional
    public int deletePost(Integer postId) {

        // 最后删除主表
        return postMapper.deletePost(postId);
    }
}