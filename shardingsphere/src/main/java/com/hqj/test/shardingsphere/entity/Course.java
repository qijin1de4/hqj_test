package com.hqj.test.shardingsphere.entity;

import lombok.Data;

@Data
public class Course {

    private Long cid;
    private Long userId;
    private String cname;
    private String cstatus;
}
