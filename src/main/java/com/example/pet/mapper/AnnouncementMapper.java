package com.example.pet.mapper;

import com.example.pet.pojo.Announcement;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface AnnouncementMapper {
    // 精简列表查询（ID/标题/标签）
    @Select("SELECT announcement_id as id, title, tag FROM announcements ORDER BY create_time DESC")
    List<Map<String, Object>> getBriefAnnouncements();

    // 根据ID获取完整公告
    @Select("SELECT * FROM announcements WHERE announcement_id = #{id}")
    Announcement getAnnouncementById(@Param("id") Integer id);

    // 新增公告（自动生成ID和时间）
    @Insert("INSERT INTO announcements(title, tag, content, create_time, update_time) " +
            "VALUES(#{title}, #{tag}, #{content}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "announcementId")
    int createAnnouncement(Announcement announcement);

    // 更新公告（自动更新修改时间）
    @Update("UPDATE announcements SET title=#{title}, tag=#{tag}, content=#{content}, " +
            "update_time=NOW() WHERE announcement_id=#{announcementId}")
    int updateAnnouncement(Announcement announcement);

    // 删除公告
    @Delete("DELETE FROM announcements WHERE announcement_id = #{id}")
    int deleteAnnouncement(@Param("id") Integer id);
}