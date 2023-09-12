package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mapper.ProductMapper;
import entity.product.Product;
import entity.product.ProductFilter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@Controller
public class ProductController {
    @Resource
    ProductMapper productMapper;

    @ResponseBody
    @GetMapping("/api/product/all")
    public List<Product> getAllProduct(){
        QueryWrapper<Product> queryWrapper = Wrappers
                .<Product>query();
        return productMapper.selectList(queryWrapper);
    }

    @ResponseBody
    @GetMapping("/api/product/profile")
    public Map<String, Object> getProductProfile(){
        QueryWrapper<Product> originWrapper = Wrappers
                .<Product>query()
                .select("origin")
                .groupBy("origin");
        QueryWrapper<Product> categoryWrapper = Wrappers
                .<Product>query()
                .select("category")
                .groupBy("category");
        // 查找表格中的最大价格和最小价格
        QueryWrapper<Product> priceWrapper = Wrappers
                .<Product>query()
                .select("max(price) as max_price", "min(price) as min_price");

        Map<String, Object> priceMap = productMapper.selectMaps(priceWrapper).get(0);
        Map<String, Object> result = new HashMap<>();
        result.put("origins", productMapper.selectObjs(originWrapper));
        result.put("categories", productMapper.selectObjs(categoryWrapper));
        result.put("max_price", priceMap.get("max_price"));
        result.put("min_price", priceMap.get("min_price"));
        result.put("msg", "请求成功！");
        return result;
    }

    @ResponseBody
    @PostMapping("/api/product/filter")
    public List<Map<String, Object>> filterProduct(
            @RequestBody ProductFilter productFilter){
        if(productFilter.categories.isEmpty()) return null;
        if(productFilter.origins.isEmpty()) return null;

        QueryWrapper<Product> queryWrapper = Wrappers.<Product>query()
                .select("on_sale", "product_id", "category", "description", "img_url", "origin", "price", "sales", "stock")
                .in("category", productFilter.categories)
                .in("origin", productFilter.origins)
                .like("description", productFilter.searchContent);
        if (productFilter.minPrice != null){
            queryWrapper.ge("price", productFilter.minPrice);
        }
        if (productFilter.maxPrice != null){
            queryWrapper.le("price", productFilter.maxPrice);
        }
        if (productFilter.remained){
            queryWrapper.gt("stock", 0);
        }
        return productMapper.selectMaps(queryWrapper);
    }

    @ResponseBody
    @GetMapping("/api/product/changeSaleState")
    public Object changeSaleState(@RequestParam("product_id") Integer productId, @RequestParam("on_sale") Integer onSale){
        Map<String, Object> result = new HashMap<>();
        try{
            Product product = productMapper.selectById(productId);
            product.setOnSale(onSale);
            productMapper.updateById(product);
            result.put("msg", "请求成功！");
        }catch (Exception e){
            result.put("msg", "服务器出错！");
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/api/product/add")
    public Object addProduct(@RequestBody Product product){
        System.out.println(product);
        Map<String, Object> result = new HashMap<>();
        try{
            productMapper.insert(product);
            result.put("msg", "请求成功！");
        }catch (Exception e){
            result.put("msg", "服务器出错！");
        }
        return result;

    }

    @ResponseBody
    @PostMapping("/api/product/update")
    public Object updateProduct(@RequestBody Product product){
        System.out.println(product);
        Map<String, Object> result = new HashMap<>();
        try{
            productMapper.updateById(product);
            result.put("msg", "请求成功！");
        }catch (Exception e){
            result.put("msg", "服务器出错！");
        }
        return result;
    }
}
