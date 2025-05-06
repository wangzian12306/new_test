package com.example.pet.service;

import com.example.pet.pojo.Fostering;
import java.util.List;

public interface FosteringService {
    // 基础查询
    Fostering getByPostId(Integer postId);
    List<Fostering> getApprovedFosterings();

    // 创建与更新
    int createFostering(Fostering fostering);
    int updateFosteringInfo(Fostering fostering);

    // 审核管理
    int updateReviewStatus(Integer postId, Integer status);
    int deleteFostering(Integer postId);
}