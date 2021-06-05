package com.konkuk.mafia;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MafiaChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(MafiaChatApplication.class, args);
    }

}
