package com.example.pet.mapper;

import com.example.pet.pojo.Fostering;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FosteringMapper {
    // 核心查询
    @Select("SELECT * FROM fosterings WHERE post_id = #{postId}")
    Fostering findByPostId(Integer postId);

    @Select("SELECT f.* FROM fosterings f JOIN posts p ON f.post_id  = p.post_id  " +
            "WHERE p.review_status  = 1 AND p.post_type  = 2") // 假设2表示送养类型
    List<Fostering> findApprovedFosterings();

    // 创建送养帖
    @Insert("INSERT INTO fosterings(post_id, user_id, content, budget, images, " +
            "adoption_method, review_status, create_time) " +
            "VALUES(#{postId}, #{userId}, #{content}, #{budget}, #{images}, " +
            "#{adoptionMethod}, 0, NOW())")
    int createFostering(Fostering fostering);

    // 审核管理
    @Update("UPDATE fosterings SET review_status = #{status}, update_time = NOW() " +
            "WHERE post_id = #{postId}")
    int updateReviewStatus(@Param("postId") Integer postId, @Param("status") Integer status);

    // 信息更新
    @Update("UPDATE fosterings SET content=#{content}, budget=#{budget}, " +
            "images=#{images}, adoption_method=#{adoptionMethod}, " +
            "update_time=NOW() WHERE post_id=#{postId}")
    int updateFosteringInfo(Fostering fostering);

    @Delete("DELETE FROM fosterings WHERE post_id = #{postId}")
    int deleteFostering(@Param("postId") Integer postId);
}