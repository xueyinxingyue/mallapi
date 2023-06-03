package com.xueyin.mallapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xueyin.mallapi.mapper")
public class MallapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallapiApplication.class, args);
    }

}
