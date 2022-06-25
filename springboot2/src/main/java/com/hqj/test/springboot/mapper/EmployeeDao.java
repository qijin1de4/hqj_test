package com.hqj.test.springboot.mapper;

import com.hqj.test.springboot.model.Employee;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
//统一在App上的@MapperScan中配置需要扫描的Mapper包路径
public interface EmployeeDao {

    Employee getEmployee(Integer id);

}
