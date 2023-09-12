package com.example.controller;

import entity.product.Product;
import entity.product.ProductFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("productservice")
public interface ProductClient {
    @ResponseBody
    @GetMapping("/api/product/all")
    List<Product> getAllProduct();

    @ResponseBody
    @GetMapping("/api/product/profile")
    Map<String, Object> getProductProfile();

    @ResponseBody
    @PostMapping("/api/product/filter")
    List<Map<String, Object>> filterProduct(@RequestBody ProductFilter productFilter);

    @ResponseBody
    @GetMapping("/api/product/changeSaleState")
    Object changeSaleState(@RequestParam("product_id") Integer productId, @RequestParam("on_sale") Integer onSale);

    @ResponseBody
    @PostMapping("/api/product/add")
    Object addProduct(@RequestBody Product product);

    @ResponseBody
    @PostMapping("/api/product/update")
    Object updateProduct(@RequestBody Product product);
}
