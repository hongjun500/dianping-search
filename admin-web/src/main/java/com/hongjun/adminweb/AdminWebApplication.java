package com.hongjun.adminweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.hongjun.mapper"})
public class AdminWebApplication {




    public static void main(String[] args) {
        SpringApplication.run(AdminWebApplication.class, args);
    }
}
