package com.example.pet.service;

import com.example.pet.pojo.Announcement;
import java.util.List;
import java.util.Map;

public interface AnnouncementService {
    // 查询功能
    List<Map<String, Object>> getBriefList();
    Announcement getDetailById(Integer id);

    // 管理功能
    int createAnnouncement(Announcement announcement);
    int updateAnnouncement(Announcement announcement);
    int deleteAnnouncement(Integer id);
}