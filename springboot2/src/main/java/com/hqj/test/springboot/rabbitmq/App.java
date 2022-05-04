package com.hqj.test.springboot.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
        SpringApplication.run(App.class, args);



    }
}
