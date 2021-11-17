package com.cqu.service.impl;

import com.cqu.dao.HelloDao;
import com.cqu.factory.BeanFactory;
import com.cqu.service.HelloService;

import java.util.List;

public class HelloServiceImpl implements HelloService {

    public HelloServiceImpl(){
        for (int i=0;i<10;i++){
            System.out.println(BeanFactory.getDao("helloDao"));
        }
    }

    // Dao注入到service中. 不直接写明导入dao，而是通过静态工厂获取
    // 即控制翻转IoC，将创建对象的权限交给静态工厂
    private HelloDao helloDao = (HelloDao)BeanFactory.getDao("helloDao");

    @Override
    public List<String> findAll(){
        return this.helloDao.findAll();
    }
}
