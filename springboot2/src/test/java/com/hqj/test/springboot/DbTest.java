package com.hqj.test.springboot;


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

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void jdbcTemplateTest() {
        Long count = jdbcTemplate.queryForObject("select count(*) from employee", Long.class);

        log.info("count is : " + count);
    }

    //@Test
//    public void redisTest() {
//        ValueOperations<String, String> ops =
//        stringRedisTemplate.opsForValue();
//        ops.set("hqjtest", "hello");
//
//        log.info("hqjtest is " + ops.get("hqjtest"));
//    }
}
