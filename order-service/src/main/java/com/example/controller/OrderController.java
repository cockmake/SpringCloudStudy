package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mapper.OrderMapper;
import com.example.mapper.ProductMapper;
import entity.order.Order;
import entity.order.OrderPageFilter;
import entity.product.Product;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@Validated
@Controller
public class OrderController {
    @Resource
    OrderMapper orderMapper;
    @Resource
    ProductMapper productMapper;
    @ResponseBody
    @GetMapping("/api/order/all")
    public List<Order> getAllOrderByUser(){
        QueryWrapper<Order> queryWrapper = Wrappers
                .<Order>query();
        return orderMapper.selectList(queryWrapper);
    }

    @ResponseBody
    @PostMapping("/api/order/filter")
    public Page<Map<String, Object>> getOrderPage(@RequestBody OrderPageFilter orderPageFilter){
        // 根据username获取表格product_id
        QueryWrapper<Order> queryWrapper2 = Wrappers
                .<Order>query()
                .eq("username", orderPageFilter.username)
                .select("product_id");

        Vector<BigInteger> product_ids = new Vector<>();
        orderMapper.selectMaps(queryWrapper2).forEach(product_id -> {
            product_ids.add((BigInteger) product_id.get("product_id"));
        });
        if (product_ids.isEmpty()) return new Page<>();

        //根据searchKey 来获取 target_ids
        QueryWrapper<Product> queryWrapper3 = Wrappers
                .<Product>query()
                .select("product_id")
                .in("product_id", product_ids)
                .like("description", orderPageFilter.searchKey);
        Vector<BigInteger> target_ids = new Vector<>();
        productMapper.selectMaps(queryWrapper3).forEach(product_id -> {
            target_ids.add((BigInteger) product_id.get("product_id"));
        });
        if (target_ids.isEmpty()) return new Page<>();

        // 根据target_ids来获取订单
        QueryWrapper<Order> queryWrapper = Wrappers
                .<Order>query()
                .eq("username", orderPageFilter.username)
                .in("product_id", target_ids);

        if (!orderPageFilter.status.equals("全部订单")){
            queryWrapper.eq("status", orderPageFilter.status);
        }
        if(orderPageFilter.dateOrder != null){
          if(orderPageFilter.dateOrder.equals("descending")){
              queryWrapper.orderByDesc("created_date");
          }
          else{
              queryWrapper.orderByAsc("created_date");
          }
        }
        if(orderPageFilter.priceOrder != null){
           if (orderPageFilter.priceOrder.equals("descending")){
               queryWrapper.orderByDesc("price");

           }
           else{
               queryWrapper.orderByAsc("price");
           }
        }
        // 获取order信息
        Page<Map<String, Object>> mapPage = orderMapper.selectMapsPage(Page.of(orderPageFilter.page, orderPageFilter.size), queryWrapper);
        target_ids.clear();
        mapPage.getRecords().forEach(data -> {
            target_ids.add((BigInteger) data.get("product_id"));
        });
        if (target_ids.isEmpty()) return new Page<>();
        // 获取product额外信息
        QueryWrapper<Product> queryWrapper4 = Wrappers
                .<Product>query()
                .select("img_url", "description")
                .in("product_id", target_ids);

        List<Map<String, Object>> mapPage2 = productMapper.selectMaps(queryWrapper4);
        // 合并mapPage, mapPage2(字段各不相同)
        for (int i = 0; i < mapPage.getRecords().size(); i++){
            mapPage.getRecords().get(i).put("img_url", mapPage2.get(i).get("img_url"));
            mapPage.getRecords().get(i).put("description", mapPage2.get(i).get("description"));
        }
        return mapPage;
    }
    @ResponseBody
    @PostMapping("/api/order/superfilter")
    public Page<Map<String, Object>> superFilter(@RequestBody OrderPageFilter orderPageFilter){
        System.out.println(orderPageFilter);
        // 获取表格所有product_id
        QueryWrapper<Order> queryWrapper2 = Wrappers
                .<Order>query()
                .select("product_id")
                .groupBy("order_id");

        Vector<BigInteger> product_ids = new Vector<>();
        orderMapper.selectMaps(queryWrapper2).forEach(product_id -> {
            product_ids.add((BigInteger) product_id.get("product_id"));
        });
        if (product_ids.isEmpty()) return new Page<>();
        //根据searchKey 来获取 target_ids
        QueryWrapper<Product> queryWrapper3 = Wrappers
                .<Product>query()
                .select("product_id")
                .in("product_id", product_ids)
                .like("description", orderPageFilter.searchKey);
        Vector<BigInteger> target_ids = new Vector<>();
        productMapper.selectMaps(queryWrapper3).forEach(product_id -> {
            target_ids.add((BigInteger) product_id.get("product_id"));
        });
        if (target_ids.isEmpty()) return new Page<>();

        // 根据target_ids来获取订单
        QueryWrapper<Order> queryWrapper = Wrappers
                .<Order>query()
                .in("product_id", target_ids);

        if (!orderPageFilter.status.equals("全部订单")){
            queryWrapper.eq("status", orderPageFilter.status);
        }
        if(orderPageFilter.dateOrder != null){
            if(orderPageFilter.dateOrder.equals("descending")){
                queryWrapper.orderByDesc("created_date");
            }
            else{
                queryWrapper.orderByAsc("created_date");
            }
        }
        if(orderPageFilter.priceOrder != null){
            if (orderPageFilter.priceOrder.equals("descending")){
                queryWrapper.orderByDesc("price");

            }
            else{
                queryWrapper.orderByAsc("price");
            }
        }
        // 获取order信息
        Page<Map<String, Object>> mapPage = orderMapper.selectMapsPage(Page.of(orderPageFilter.page, orderPageFilter.size), queryWrapper);
        target_ids.clear();
        mapPage.getRecords().forEach(data -> {
            target_ids.add((BigInteger) data.get("product_id"));
        });
        if (target_ids.isEmpty()) return new Page<>();
        // 获取product额外信息
        QueryWrapper<Product> queryWrapper4 = Wrappers
                .<Product>query()
                .select("product_id", "img_url", "description")
                .in("product_id", target_ids);

        List<Map<String, Object>> mapPage2 = productMapper.selectMaps(queryWrapper4);
        // 合并mapPage, mapPage2(字段各不相同), 但是mapPage2 size较小
        Map<BigInteger, Map<String, Object>> tmpMap = new HashMap<>();
        for (Map<String, Object> stringObjectMap : mapPage2) {
            tmpMap.put((BigInteger) stringObjectMap.get("product_id"), stringObjectMap);
        }

        for (int i = 0; i < mapPage.getRecords().size(); i++){
            mapPage.getRecords().get(i).put("img_url",
                    tmpMap.get((BigInteger) mapPage.getRecords().get(i).get("product_id")).get("img_url"));
            mapPage.getRecords().get(i).put("description",
                    tmpMap.get((BigInteger) mapPage.getRecords().get(i).get("product_id")).get("description"));
        }
        return mapPage;

    }

    @ResponseBody
    @GetMapping("/api/order/delete")
    public Object deleteOrder(@RequestParam("order_id") BigInteger orderId){
        Map<String, String> map = new HashMap<>();
        try{
            orderMapper.deleteById(orderId);
            map.put("msg", "操作成功！");
        }catch (Exception e){
            map.put("msg", "服务器出错！");
        }
        return map;
    };

    @ResponseBody
    @GetMapping("/api/order/confirm")
    public Object confirmOrder(@RequestParam("order_id") BigInteger orderId){
        Map<String, String> map = new HashMap<>();
        try{
            Order order = orderMapper.selectById(orderId);
            order.setStatus("已完成");
            orderMapper.updateById(order);
            map.put("msg", "操作成功！");
        }catch (Exception e){
            map.put("msg", "服务器出错！");
        }
        return map;
    };


    @ResponseBody
    @PostMapping("/api/order/add")
    public String addOrder(@RequestBody Order order){
        System.out.println(order);
        try {
            orderMapper.insert(order);
            return "操作成功！";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "服务器出错！";
        }
    }
}
