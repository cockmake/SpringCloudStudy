package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        // 网关和服务的跨域只能配置一个
        return httpSecurity
//                .cors(conf -> {
//                    CorsConfiguration cors = new CorsConfiguration();
//                    cors.addAllowedOriginPattern("*");
//                    cors.setAllowCredentials(true);
//                    cors.addAllowedMethod("*");
//                    cors.addAllowedHeader("*");
//                    cors.addExposedHeader("*");
//                    UrlBasedCorsConfigurationSource source =
//                            new UrlBasedCorsConfigurationSource();
//                    source.registerCorsConfiguration("/**", cors);
//                    conf.configurationSource(source);
//                })
                .csrf(CsrfConfigurer::disable)
                .build();
    }
}
