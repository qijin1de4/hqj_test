package com.hqj.test.shardingsphere.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_udict")
public class UserDict {
    private Long dictid;
    private String ustatus;
    private String uvalue;
}
