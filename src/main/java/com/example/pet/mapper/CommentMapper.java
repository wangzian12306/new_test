package com.example.pet.mapper;

import com.example.pet.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentMapper {
    // 根据帖子ID获取所有评论（按时间倒序）
    @Select("SELECT * FROM comments WHERE post_id = #{postId} ORDER BY create_time DESC")
    List<Comment> findByPostId(@Param("postId") Integer postId);

    // 新增评论（审核状态默认0）
    @Insert("INSERT INTO comments(user_id, content, post_id, create_time, review_status) " +
            "VALUES(#{userId}, #{content}, #{postId}, NOW(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    int createComment(Comment comment);

    // 批量审核评论
    @Update("<script>" +
            "UPDATE comments SET review_status = #{status} WHERE comment_id IN " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int batchUpdateStatus(@Param("ids") List<Integer> commentIds, @Param("status") Integer status);

    // 单个评论审核
    @Update("UPDATE comments SET review_status = #{status} WHERE comment_id = #{commentId}")
    int updateReviewStatus(@Param("commentId") Integer commentId, @Param("status") Integer status);

    // 单个删除
    @Delete("DELETE FROM comments WHERE comment_id = #{commentId}")
    int deleteComment(@Param("commentId") Integer commentId);
}