package com.example.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@LoadBalancerClients
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    // 负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
