package com.example.pet.service.impl;

import com.example.pet.mapper.AdoptionMapper;
import com.example.pet.pojo.Adoption;
import com.example.pet.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdoptionServiceimpl implements AdoptionService {

    @Autowired
    private AdoptionMapper adoptionMapper;

    @Override
    public Adoption getByPostId(Integer postId) {
        return adoptionMapper.findByPostId(postId);
    }

    @Override
    public List<Adoption> getApprovedAdoptions() {
        return adoptionMapper.findApprovedAdoptions();
    }

    @Override
    public int createAdoption(Adoption adoption) {
        // 参数校验
        if(adoption.getContent()  == null || adoption.getContent().length()  > 800) {
            throw new IllegalArgumentException("内容不能为空且不超过800字");
        }
        if(adoption.getLocation()  == null || adoption.getLocation().length()  > 30) {
            throw new IllegalArgumentException("所在地不能为空且不超过30字");
        }
        return adoptionMapper.createAdoption(adoption);
    }

    @Override
    public int updateAdoptionInfo(Adoption adoption) {
        // 存在性校验
        Adoption existing = adoptionMapper.findByPostId(adoption.getPostId());
        if(existing == null) {
            throw new RuntimeException("领养帖子不存在");
        }
        return adoptionMapper.updateAdoptionInfo(adoption);
    }

    @Override
    public int updateReviewStatus(Integer postId, Integer status) {
        if(status < 0 || status > 2) {
            throw new IllegalArgumentException("审核状态值不合法");
        }
        return adoptionMapper.updateReviewStatus(postId,  status);
    }

    @Override
    @Transactional
    public int deleteAdoption(Integer postId) {
        // 再删除领养记录
        int result = adoptionMapper.deleteAdoption(postId);
        return result;
    }
}