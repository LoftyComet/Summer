package com.cqu.myspring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 添加对象：类
@Retention(RetentionPolicy.RUNTIME) // 运行时机：运行时
public @interface Component {
    String value() default ""; // 默认为空
}
