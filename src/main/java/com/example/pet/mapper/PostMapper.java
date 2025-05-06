package com.example.pet.mapper;

import com.example.pet.pojo.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
public interface PostMapper {

    // 获取所有帖子标题和ID（返回Map列表）
    @Select("SELECT post_id as postId, title FROM posts")
    List<Map<String, Object>> getAllPostTitlesAndIds();

    // 根据帖子ID查询完整帖子信息
    @Select("SELECT * FROM posts WHERE post_id = #{postId}")
    Post getPostById(@Param("postId") Integer postId);

    // 新建帖子（自动生成ID，审核状态默认为0，时间自动生成）
    @Insert("INSERT INTO posts(post_type, title, user_id, likes, content, images, create_time, review_status) " +
            "VALUES(#{postType}, #{title}, #{userId}, 0, #{content}, #{images}, NOW(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    int createPost(Post post);

    // 修改帖子审核状态
    @Update("UPDATE posts SET review_status = #{status} WHERE post_id = #{postId}")
    int updateReviewStatus(@Param("postId") Integer postId, @Param("status") Integer status);

    // 帖子点赞（原子操作，避免并发问题）
    @Update("UPDATE posts SET likes = likes + 1 WHERE post_id = #{postId}")
    int incrementLikes(@Param("postId") Integer postId);

    @Delete("DELETE FROM posts WHERE post_id = #{postId}")
    int deletePost(@Param("postId") Integer postId);
}
