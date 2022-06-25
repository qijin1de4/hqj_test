package com.hqj.test.springboot.model;

import lombok.Data;

@Data
public class Employee {
    private Integer id;
    String lastName;
    String firstName;
    String city;
    String designation;
}
