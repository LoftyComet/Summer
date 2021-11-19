package com.cqu.myspring;

import java.util.Iterator;
import java.util.Set;

public class MyAnnotationConfigApplicationContext {
    // 传入包名以便获取包里所有类
    public MyAnnotationConfigApplicationContext(String pack) {
        // 遍历包，找到目标类(原材料)
        Set<BeanDefinition> beanDefinitions = findBeanDefinitions(pack);
    }

    public Set<BeanDefinition> findBeanDefinitions(String pack){
        // 1.获取包下所有类
        // 调用借鉴的MyTools
        Set<Class<?>> classes = MyTools.getClasses(pack);
        // 2.遍历这些类
        // 获取迭代器
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            // 获得注解
            Component componentAnnotation = clazz.getAnnotation(Component.class);
            // 加了注解,即为目标类
            if (componentAnnotation!=null) {
                // 获取Component注释的值
                String beanName = componentAnnotation.value();
                if ("".equals(beanName)){
                    // 获取类名小写

                }
            }
        }
        // 3.将这些类封装成beanDefinition
        return null;
    }
}
