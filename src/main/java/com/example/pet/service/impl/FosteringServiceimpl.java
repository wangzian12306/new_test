package com.example.pet.service.impl;

import com.example.pet.mapper.FosteringMapper;
import com.example.pet.pojo.Fostering;
import com.example.pet.service.FosteringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FosteringServiceimpl implements FosteringService {

    @Autowired
    private FosteringMapper fosteringMapper;

    @Override
    public Fostering getByPostId(Integer postId) {
        return fosteringMapper.findByPostId(postId);
    }

    @Override
    public List<Fostering> getApprovedFosterings() {
        return fosteringMapper.findApprovedFosterings();
    }

    @Override
    public int createFostering(Fostering fostering) {
        // 业务校验
        if(fostering.getAdoptionMethod()  == null || fostering.getAdoptionMethod().length()  > 20) {
            throw new IllegalArgumentException("领养方式不能为空且不超过20字");
        }
        if(fostering.getBudget()  != null && fostering.getBudget()  < 0) {
            throw new IllegalArgumentException("预算不能为负数");
        }
        return fosteringMapper.createFostering(fostering);
    }

    @Override
    public int updateFosteringInfo(Fostering fostering) {
        // 存在性验证
        if(fosteringMapper.findByPostId(fostering.getPostId())  == null) {
            throw new RuntimeException("送养帖子不存在");
        }
        return fosteringMapper.updateFosteringInfo(fostering);
    }

    @Override
    public int updateReviewStatus(Integer postId, Integer status) {
        if(status < 0 || status > 2) {
            throw new IllegalArgumentException("审核状态必须是0-2之间的整数");
        }
        return fosteringMapper.updateReviewStatus(postId,  status);
    }


    @Override
    @Transactional
    public int deleteFostering(Integer postId) {
        int result = fosteringMapper.deleteFostering(postId);
        return result;
    }}