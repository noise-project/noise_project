package com.project.demo;

import com.project.demo.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class  SpringMysqlApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringMysqlApplication.class, args);
	}

}
