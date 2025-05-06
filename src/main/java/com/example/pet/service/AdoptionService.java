package com.example.pet.service;

import com.example.pet.pojo.Adoption;
import java.util.List;

public interface AdoptionService {
    // 查询功能
    Adoption getByPostId(Integer postId);
    List<Adoption> getApprovedAdoptions();

    // 创建与更新
    int createAdoption(Adoption adoption);
    int updateAdoptionInfo(Adoption adoption);

    // 审核管理
    int updateReviewStatus(Integer postId, Integer status);
    int deleteAdoption(Integer postId);
}