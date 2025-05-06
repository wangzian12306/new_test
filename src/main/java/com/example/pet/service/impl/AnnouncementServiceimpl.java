package com.example.pet.service.impl;

import com.example.pet.mapper.AnnouncementMapper;
import com.example.pet.pojo.Announcement;
import com.example.pet.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AnnouncementServiceimpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Map<String, Object>> getBriefList() {
        return announcementMapper.getBriefAnnouncements();
    }

    @Override
    public Announcement getDetailById(Integer id) {
        return announcementMapper.getAnnouncementById(id);
    }

    @Override
    public int createAnnouncement(Announcement announcement) {
        // 参数校验
        if(announcement.getTitle()  == null || announcement.getTitle().length()  > 30) {
            throw new IllegalArgumentException("标题不能为空且不超过30字");
        }
        if(announcement.getTag()  != null && announcement.getTag().length()  > 10) {
            throw new IllegalArgumentException("标签长度不能超过10字");
        }
        return announcementMapper.createAnnouncement(announcement);
    }

    @Override
    public int updateAnnouncement(Announcement announcement) {
        // 存在性校验
        if(announcementMapper.getAnnouncementById(announcement.getAnnouncementId())  == null) {
            throw new RuntimeException("公告不存在");
        }
        return announcementMapper.updateAnnouncement(announcement);
    }

    @Override
    public int deleteAnnouncement(Integer id) {
        return announcementMapper.deleteAnnouncement(id);
    }
}