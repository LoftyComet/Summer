package com.cqu.dao.impl;

import com.cqu.dao.HelloDao;

import java.util.Arrays;
import java.util.List;

public class HelloDaoImpl implements HelloDao {
    @Override
    public List<String> findAll(){
        // 模拟查询数据库
        return Arrays.asList("1","2","3");
    }
}
