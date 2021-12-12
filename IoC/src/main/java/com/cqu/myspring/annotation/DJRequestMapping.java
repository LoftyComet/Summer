package com.cqu.myspring.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DJRequestMapping {
    String value() default "";
    String method() default "GET";
}
