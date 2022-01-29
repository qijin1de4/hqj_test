
CREATE TABLE `course_1` (
                            `cid` bigint(20) NOT NULL COMMENT '课程ID',
                            `cname` varchar(50) NOT NULL COMMENT '课程名字',
                            `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                            `cstatus` varchar(20) NOT NULL COMMENT '课程状态',
                            PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `course_2` (
                            `cid` bigint(20) NOT NULL COMMENT '课程ID',
                            `cname` varchar(50) NOT NULL COMMENT '课程名字',
                            `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                            `cstatus` varchar(20) NOT NULL COMMENT '课程状态',
                            PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user` (
                          `user_id` bigint(20) NOT NULL,
                          `username` varchar(100) NOT NULL,
                          `ustatus` varchar(50) NOT NULL,
                          PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_udict` (
                           `dictid` bigint(20) NOT NULL,
                           `ustatus` varchar(100) NOT NULL,
                           `uvalue` varchar(100) NOT NULL,
                           PRIMARY KEY (`dictid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户字典表';