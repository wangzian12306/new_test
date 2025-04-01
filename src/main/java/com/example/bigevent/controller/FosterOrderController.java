package com.example.bigevent.controller;

import com.example.bigevent.pojo.FosterOrder;
import com.example.bigevent.pojo.Result;
import com.example.bigevent.service.FosterOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fosterOrder")
public class FosterOrderController {
    @Autowired
    private FosterOrderService orderService;

    @PostMapping("/create")
    public Result create(@RequestBody @Validated FosterOrder order) {
        orderService.create(order);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(@PathVariable String orderId) {
        orderService.delete(orderId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated FosterOrder order) {
        orderService.update(order);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<FosterOrder>> list() {
        return Result.success(orderService.list());
    }

    @GetMapping("/getbyid")
    public Result<FosterOrder> getById(@PathVariable String orderId) {
        return Result.success(orderService.getById(orderId));
    }
}