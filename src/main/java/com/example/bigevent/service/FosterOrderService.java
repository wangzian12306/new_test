package com.example.bigevent.service;

import com.example.bigevent.pojo.FosterOrder;
import java.util.List;

public interface FosterOrderService {
    void create(FosterOrder order);
    void delete(String orderId);
    void update(FosterOrder order);
    List<FosterOrder> list();
    FosterOrder getById(String orderId);
}