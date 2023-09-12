package com.example.service;

import com.example.controller.OrderClient;
import com.example.controller.ProductClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Enumeration;
import java.util.stream.Collectors;

// 主要用于转发请求
@Controller
public class ServiceController {
    @Resource
    RestTemplate template;
    // 通过FeignClient调用其他服务
    @Resource
    OrderClient orderClient;
    @Resource
    ProductClient productClient;

    @ResponseBody
    @GetMapping("/api/**")
    public String apiProductAll(HttpServletRequest request){
        String path = request.getRequestURI().substring(4);
        String instance = path.startsWith("/order") ? "orderservice" : "productservice";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://" + instance + "/api" + path);
        // 获取并设置所有请求参数
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String[] parameterValues = request.getParameterValues(parameterName);
            for (String parameterValue : parameterValues) {
                builder.queryParam(parameterName, parameterValue);
            }
        }
        try{
            return template.getForObject(builder.toUriString(), String.class);
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @ResponseBody
    @PostMapping("/api/**")
    public String apiProductFilter(HttpServletRequest request){
        String path = request.getRequestURI().substring(4);
        String instance = path.startsWith("/order") ? "orderservice" : "productservice";
        try{
            // 获取原始请求体
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            // 创建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // 创建HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            // 使用RestTemplate发送POST请求
            return template.postForObject("http://" + instance + "/api" + path, entity, String.class);
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
