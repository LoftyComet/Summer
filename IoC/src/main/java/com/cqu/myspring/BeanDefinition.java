package com.cqu.myspring;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // 全参构造
public class BeanDefinition {
    // 封装的类来保存beanName 和 类别
    private String beanName;
    private Class beanClass;
}
