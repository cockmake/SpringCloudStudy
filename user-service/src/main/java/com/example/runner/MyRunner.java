package com.example.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements ApplicationRunner {
    @Value("${server.port}")
    public int port;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("服务运行在：" + "http://localhost:" + port + "/");
        System.out.println("服务接口文档：" + "http://localhost:" + port + "/swagger-ui/index.html");
    }
}
