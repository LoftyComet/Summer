package com.cqu.myspring.model;


import com.cqu.myspring.annotation.ResponseType;

import java.lang.reflect.Method;
// 用来保存beanname和类别返回类型的封装类
public class MVCMapping {
    protected Method method;
    protected ResponseType responseType;
    protected Object cla;


    public MVCMapping(Method method, ResponseType responseType, Object cla){
        this.method = method;
        this.responseType = responseType;
        this.cla = cla;
    }

    public Method getMethod() {
        return method;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public Object getCla() {
        return cla;
    }
}
