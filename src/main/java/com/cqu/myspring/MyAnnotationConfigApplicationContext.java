package com.cqu.myspring;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MyAnnotationConfigApplicationContext {
    // 缓存保存ioc
    private Map<String,Object> ioc = new HashMap<>();

    // 传入包名以便获取包里所有类
    public MyAnnotationConfigApplicationContext(String pack) {
        // 遍历包，找到目标类(原材料)
        Set<BeanDefinition> beanDefinitions = findBeanDefinitions(pack);
        // Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        // while(iterator.hasNext()) {
        //     BeanDefinition next = iterator.next();
        //     System.out.println(next);
        // }

        // 根据原材料创建bean
        createObject(beanDefinitions);
        // 自动装载
        autowireObject(beanDefinitions);
    }

    public void autowireObject(Set<BeanDefinition> beanDefinitions) {
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while(iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            Class clazz = beanDefinition.getBeanClass();
            Field[] declaredFields = clazz.getDeclaredFields(); // 获取所有属性
            for (Field declaredField : declaredFields) {
                Autowired annotation = declaredField.getAnnotation(Autowired.class); // 判断是否有Autowired注解
                if (annotation != null) { // 判断是否有Autowired注解
                    Qualifier qualifier = declaredField.getAnnotation(Qualifier.class);
                    if (qualifier != null) {
                        try {
                            // byName
                            String beanName = qualifier.value();
                            Object bean = getBean(beanName); // 属性
                            String fieldName = declaredField.getName();
                            String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1); // 获取set方法名
                            Method method = clazz.getMethod(methodName,declaredField.getType()); // 获取方法
                            Object object = getBean(beanDefinition.getBeanName()); // 对象
                            method.invoke(object, bean);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        // byType
                    }
                }
            }
        }
    }

    public Object getBean(String beanName){
        return ioc.get(beanName);
    }

    public void createObject(Set<BeanDefinition> beanDefinitions){
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while(iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            Class clazz = beanDefinition.getBeanClass();
            String beanName = beanDefinition.getBeanName();
            try {
                // 创建的对象
                Object object = clazz.getConstructor().newInstance();
                // 先完成属性赋值
                Field[] declaredFields = clazz.getDeclaredFields(); // 获取所有属性
                for (Field declaredField : declaredFields) {
                    Value valueAnnotation = declaredField.getAnnotation(Value.class);
                    if (valueAnnotation != null) { // 有注解
                        String value = valueAnnotation.value();
                        String fieldName = declaredField.getName();
                        String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1); // 获取set方法名
                        Method method = clazz.getMethod(methodName,declaredField.getType()); // 获取方法
                        // 完成数据类型转换
                        Object val = null;
                        switch (declaredField.getType().getName()){
                            case  "java.lang.Integer":
                                val = Integer.parseInt(value);
                                break;
                            case  "java.lang.String":
                                val = value;
                                break;
                            case "java.lang.Float":
                                val = Float.parseFloat(value);
                                break;
                        }
                        method.invoke(object,val);
                    }

                }

                // 放入ioc容器
                ioc.put(beanName,object);
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

    public Set<BeanDefinition> findBeanDefinitions(String pack){
        // 1.获取包下所有类
        // 调用借鉴的MyTools
        Set<Class<?>> classes = MyTools.getClasses(pack);
        // 2.遍历这些类
        // 获取迭代器
        Iterator<Class<?>> iterator = classes.iterator();

        Set<BeanDefinition> beanDefinitions = new HashSet<>();
        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            // 获得注解
            Component componentAnnotation = clazz.getAnnotation(Component.class);
            // 加了注解,即为目标类
            if (componentAnnotation!=null) {
                // 获取Component注释的值
                String beanName = componentAnnotation.value();
                if ("".equals(beanName)){ // 注解内没写
                    // 获取类名小写用作beanName
                    String className = clazz.getName().replaceAll(clazz.getPackage().getName()+".", ""); // 先取包名再替换为空即可去除包名前缀
                    beanName = className.substring(0, 1).toLowerCase()+className.substring(1); //将第一个字母变为小写                
                }
        // 3.将这些类封装成beanDefinition

                beanDefinitions.add(new BeanDefinition(beanName, clazz));
            }
            
        }
        return beanDefinitions;
    }
}
