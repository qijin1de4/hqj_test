package com.hqj.test.shardingsphere;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hqj.test.shardingsphere.entity.Course;
import com.hqj.test.shardingsphere.entity.User;
import com.hqj.test.shardingsphere.entity.UserDict;
import com.hqj.test.shardingsphere.mapper.CourseMapper;
import com.hqj.test.shardingsphere.mapper.UserDictMapper;
import com.hqj.test.shardingsphere.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDictMapper userDictMapper;

    private Random rand = new Random();

    @Test
    public void addCourse() {
        for(long i = 0; i < 100; i++) {
            Course course = new Course();
            course.setCname("hello"+i);
            course.setUserId(i);
            course.setCstatus("normal"+i);
            courseMapper.insert(course);
        }
    }

    @Test
    public void findCourse() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", 694134171034976256L);
        queryWrapper.eq("user_id", 83L);
        Course course = courseMapper.selectOne(queryWrapper);

        System.out.println(course);
    }

    @Test
    public void addUser() {
        IntStream.range(1,100).forEach(i -> {
            User user = new User();
            user.setUsername("username" + i);
            user.setUstatus("ustatus" + i);
            userMapper.insert(user);
        });
    }

    @Test
    public void addUDict() {
        IntStream.range(1,100).forEach(i -> {
            final UserDict userDict = new UserDict();
            userDict.setUstatus("status"+i);
            userDict.setUvalue("value"+i);
            userDictMapper.insert(userDict);
        });
    }

    @Test
    public void deleteUDict() {
        final QueryWrapper<UserDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dictid", 694152392484061184L);
        userDictMapper.delete(queryWrapper);
    }
}
