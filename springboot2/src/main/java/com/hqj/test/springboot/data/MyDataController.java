package com.hqj.test.springboot.data;

import com.hqj.test.springboot.mapper.EmployeeDao;
import com.hqj.test.springboot.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mydata")
public class MyDataController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeDao employeeDao;

    @GetMapping("/employeeCount")
    public Long employeeCount() {
        return jdbcTemplate.queryForObject("select count(*) from employee", Long.class);
    }

    @ResponseBody
    @GetMapping("/getEmployee/{id}")
    public Employee getEmployee(@PathVariable Integer id) {
        return employeeDao.getEmployee(id);
    }

}
