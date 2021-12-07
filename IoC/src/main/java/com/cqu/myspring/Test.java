package com.cqu.myspring;

public class Test {
    public static void main(String[] args) {
        MyAnnotationConfigApplicationContext applicationContext = new MyAnnotationConfigApplicationContext("com.cqu.myspring.entity");
        System.out.println(applicationContext.getBean("account"));
    }
}
