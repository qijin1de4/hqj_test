package com.hqj.test.springboot.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.Optional;

/**
 * 测试SpringBoot运行监听器
 */
@Slf4j
@Order(2147483645)
public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    MySpringApplicationRunListener(SpringApplication application, String[] args) {
        log.info("application: {}", application);
        log.info("args: {}", Optional.ofNullable(args).map(Arrays::toString));
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("context prepared!");
    }


}
