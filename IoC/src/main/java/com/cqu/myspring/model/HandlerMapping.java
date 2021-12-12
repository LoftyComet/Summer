package com.cqu.myspring.model;

import com.cqu.myspring.annotation.*;
import com.cqu.myspring.utils.UrlAndMethod;
import com.cqu.myspring.DJ_IoC;


import javax.servlet.ServletConfig;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;
// 处理地址映射关系
public class HandlerMapping {

    private Properties properties = new Properties();

    private DJ_IoC ioc = null;

    private Map<UrlAndMethod, MVCMapping> handlerMapping = new HashMap<>();

    public HandlerMapping(ServletConfig config) {
        loadConfig(config);
        loadBeans();
        loadMapping();
    }

    public Map<UrlAndMethod, MVCMapping> getAllMappings(){
        return handlerMapping;
    }
    private void loadBeans(){
        try {
            ioc = new DJ_IoC(properties.getProperty("factory"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadMapping()  {
//        System.out.println("load mapping");
        for(Map.Entry<String, Object> entry : ioc.getAllObject()){
            Class<?> cla = entry.getValue().getClass();

            String baseUrl = "";
            if(cla.isAnnotationPresent(DJRequestMapping.class)){
                baseUrl = cla.getAnnotation(DJRequestMapping.class).value();
            }

            Method[] methods = cla.getMethods();
            for(Method method : methods){
                if(!method.isAnnotationPresent(DJRequestMapping.class)){
                    continue;
                }
                String url = method.getAnnotation(DJRequestMapping.class).value();
                String requestMethod = method.getAnnotation(DJRequestMapping.class).method();
                if(method.isAnnotationPresent(ResponseBody.class)){
                    handlerMapping.put(new UrlAndMethod(baseUrl+url,requestMethod), new MVCMapping(method, ResponseType.Text, ioc.getBean(cla.getName())));
                }
                else if (method.isAnnotationPresent(ResponseView.class)){
                    handlerMapping.put(new UrlAndMethod(baseUrl+url,requestMethod), new MVCMapping(method, ResponseType.View, ioc.getBean(cla.getName())));
                }

            }

        }
    }


    private void loadConfig(ServletConfig config){
        String configLocation = config.getInitParameter("Configuration");
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream(configLocation);
        try {
            properties.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

