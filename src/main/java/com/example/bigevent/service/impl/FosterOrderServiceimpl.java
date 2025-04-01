package com.example.bigevent.service.impl;

import com.example.bigevent.mapper.FosterOrderMapper;
import com.example.bigevent.pojo.FosterOrder;
import com.example.bigevent.pojo.ThreadLocalUtil;
import com.example.bigevent.service.FosterOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FosterOrderServiceimpl implements FosterOrderService {
    @Autowired
    private FosterOrderMapper orderMapper;

    @Override
    public void create(FosterOrder order) {

        Map<String, Object> claims = ThreadLocalUtil.get();
        order.setOrderId(UUID.randomUUID().toString());
        order.setCreatorId((Integer)  claims.get("id"));
        order.setStatus(FosterOrder.Status.PENDING.name());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());

        // 计算天数
        long days = order.getEndTime().getDayOfYear()  -
                order.getStartTime().getDayOfYear();
        order.setFosterDays((int)  days);

        orderMapper.insert(order);
    }

    @Override
    public void delete(String orderId) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id=(Integer)map.get("id");
        orderMapper.delete(orderId,  id);
    }

    @Override
    public void update(FosterOrder order) {
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.update(order);
    }

    @Override
    public List<FosterOrder> list() {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id=(Integer)map.get("id");
        return orderMapper.listByCreator(id);
    }

    @Override
    public FosterOrder getById(String orderId) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id=(Integer)map.get("id");
        return orderMapper.selectByIdAndUser(orderId,  id);
    }
}