package com.cqu.myspring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自动装载
@Target(ElementType.FIELD) // 给属性加
@Retention(RetentionPolicy.RUNTIME) // 运行时加
public @interface Autowired { //不用添加string，因为Autowired注解不需要给它赋值
}
