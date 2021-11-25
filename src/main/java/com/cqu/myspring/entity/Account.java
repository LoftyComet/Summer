package com.cqu.myspring.entity;

import com.cqu.myspring.Component;
import com.cqu.myspring.Value;
import lombok.Data;

// 添加自定义注解
@Component
@Data
public class Account {
    @Value("1")
    private Integer id;
    @Value("DJJ")
    private String name;
    @Value("20")
    private Integer age;
}
