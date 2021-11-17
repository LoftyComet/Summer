package com.cqu.factory;

import com.cqu.dao.HelloDao;
import com.cqu.dao.impl.HelloDaoImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BeanFactory {

    private static Properties properties;
    // 使用缓存实现单例模式，节省资源
    private static Map<String,Object> cache = new HashMap<>();
    // 读取配置文件并保存
    static {
        properties = new Properties();
        try {
            properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("factory.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getDao(String beanName){
        // 从缓存中获取bean
        // 先判断是否存在
        if (!cache.containsKey(beanName)){
            // 不存在，将bean存入缓存
            // 加锁，避免并发出现问题
            synchronized (BeanFactory.class){
                if (!cache.containsKey(beanName)){
                    try {
                        // 反射机制创建对象
                        String value = properties.getProperty(beanName);
                        Class clazz = Class.forName(value);
                        Object object = clazz.getConstructor(null).newInstance(null);
                        cache.put(beanName,object);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }



        }
        return cache.get(beanName);
    }
}
