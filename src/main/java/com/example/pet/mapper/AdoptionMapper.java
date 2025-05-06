package com.example.pet.mapper;
import com.example.pet.pojo.Adoption;
import org.apache.ibatis.annotations.*;

import java.util.List;
public interface AdoptionMapper {

    // 基础查询
    @Select("SELECT * FROM adoptions WHERE post_id = #{postId}")
    Adoption findByPostId(@Param("postId") Integer postId);

    @Select("SELECT a.* FROM adoptions a JOIN posts p ON a.post_id  = p.post_id  WHERE p.review_status  = 1")
    List<Adoption> findApprovedAdoptions();

    // 创建领养帖（关联posts表）
    @Insert("INSERT INTO adoptions(post_id, user_id, content, budget_or_situation, gender, age, " +
            "occupation, location, adoption_method, review_status, create_time) " +
            "VALUES(#{postId}, #{userId}, #{content}, #{budgetOrSituation}, #{gender}, #{age}, " +
            "#{occupation}, #{location}, #{adoptionMethod}, 0, NOW())")
    int createAdoption(Adoption adoption);

    // 审核管理
    @Update("UPDATE adoptions SET review_status = #{status}, update_time = NOW() WHERE post_id = #{postId}")
    int updateReviewStatus(@Param("postId") Integer postId, @Param("status") Integer status);

    // 信息更新
    @Update("UPDATE adoptions SET content=#{content}, budget_or_situation=#{budgetOrSituation}, " +
            "gender=#{gender}, age=#{age}, occupation=#{occupation}, location=#{location}, " +
            "adoption_method=#{adoptionMethod}, update_time=NOW() WHERE post_id=#{postId}")
    int updateAdoptionInfo(Adoption adoption);

    @Delete("DELETE FROM adoptions WHERE post_id = #{postId}")
    int deleteAdoption(@Param("postId") Integer postId);
}
