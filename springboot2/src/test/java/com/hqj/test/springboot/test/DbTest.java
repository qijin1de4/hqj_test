package com.hqj.test.springboot.test;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootTest
public class DbTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void jdbcTemplateTest() {
        Long count = jdbcTemplate.queryForObject("select count(*) from employee", Long.class);

        log.info("count is : " + count);
    }

    @Test
    public void simpleTest() {
        log.info("hello");
    }
}
