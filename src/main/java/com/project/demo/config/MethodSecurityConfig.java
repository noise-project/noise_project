package com.project.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class MethodSecurityConfig {
    //존재하는 것 만으로 @PreAuthorize가 활성화 됌
}
