package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.order.Order;
import entity.order.OrderPageFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
@FeignClient("orderservice")
public interface OrderClient {
    @ResponseBody
    @GetMapping("/api/order/all")
    List<Order> getAllOrderByUser(@RequestParam String username);

    @ResponseBody
    @PostMapping("/api/order/filter")
    Page<Map<String, Object>> getOrderPage(@RequestBody OrderPageFilter orderPageFilter);

    @ResponseBody
    @GetMapping("/api/order/delete")
    Object deleteOrder(@RequestParam("order_id") BigInteger orderId);

    @ResponseBody
    @GetMapping("/api/order/confirm")
    Object confirmOrder(@RequestParam("order_id") BigInteger orderId);
}
