package com.cqu.myspring.entity;

import com.cqu.myspring.Component;
import com.cqu.myspring.Value;

// 添加自定义注解
@Component
public class Account {
    @Value("1")
    private Integer id;
    @Value("DJJ")
    private String name;
    @Value("20")
    private Integer age;
}
