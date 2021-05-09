package com.cjd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cjd.mapper")
public class BbsCjdApplication {

    public static void main(String[] args) {
        SpringApplication.run(BbsCjdApplication.class, args);
    }

}
