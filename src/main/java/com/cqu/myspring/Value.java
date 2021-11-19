package com.cqu.myspring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 添加对象：成员变量
@Retention(RetentionPolicy.RUNTIME) // 添加时间：运行时
public @interface Value {
    String value(); // 不需要默认值，必须传入值
}
