package com.hqj.test.springboot;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@MapperScan("com.hqj.test.springboot.mapper")
@Slf4j
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
        SpringApplication.run(App.class, args);

        log.info("Successfully started!");


    }
}
