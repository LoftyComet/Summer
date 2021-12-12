package com.cqu.myspring.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD}) // 添加对象：类 方法
@Retention(RetentionPolicy.RUNTIME) // 运行时机：运行时
@Documented
public @interface RequestMapping {
    String value() default "";
    String method() default "GET";
}
