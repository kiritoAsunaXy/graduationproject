package com.xuyu.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Myspringbootweb01Application {
    public static void main(String[] args) {
        SpringApplication.run(Myspringbootweb01Application.class, args);
    }
}
