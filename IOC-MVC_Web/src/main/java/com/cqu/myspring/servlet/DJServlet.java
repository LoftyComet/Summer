package com.cqu.myspring.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cqu.myspring.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.ServletConfig;
import java.lang.reflect.Method;
import java.util.*;

import com.cqu.myspring.model.DJModelView;
import org.apache.commons.fileupload.FileItem;
import com.cqu.myspring.model.HandlerMapping;
import com.cqu.myspring.model.MVCMapping;
import com.cqu.myspring.utils.Uploadhandler;
import com.cqu.myspring.utils.UrlAndMethod;


public class DJServlet extends HttpServlet {
    // 程序的入口，处理不同的请求

    Map<UrlAndMethod, MVCMapping> handlerMapping;

    @Override
    public void init(ServletConfig config) {
        try {
            handlerMapping = new HandlerMapping(config).getAllMappings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp, "POST");
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp, String requestMethod) throws IOException {
        String url = req.getRequestURI().replace(req.getContextPath(), "").replace("/mvc","");
//        System.out.println("url: " + url);
        UrlAndMethod urlAndMethod = new UrlAndMethod(url, requestMethod);

        if(!handlerMapping.containsKey(urlAndMethod)){
            resp.getWriter().write("404 Not Found!");
        }

        List<FileItem> fileItems = null;

        try{
            fileItems = Uploadhandler.getAllFiles(req);
        } catch (Exception e) {
            e.printStackTrace();
        }


        MVCMapping mapping = handlerMapping.get(urlAndMethod);

        Method method = mapping.getMethod();
        ResponseType type = mapping.getResponseType();

        Class<?>[] paramTypes = method.getParameterTypes();
        Map<String, String[]> paramMap = req.getParameterMap();

//        System.out.println("start");
//        for(String[] param: paramMap.values()){
//            String t = Arrays.toString(param);
//            System.out.println(t);
//        }
//        System.out.println("finish");

        Object[] paramValues = new Object[paramTypes.length];

        switch (requestMethod) {
            case "POST":
                boolean fileType = false;
                int fileIndex = 0;
                for(int i = 0; i < paramTypes.length; i++) {
                    // 解析类型
                    String requestParam = paramTypes[i].getSimpleName();
                    if (requestParam.equals("FileItem")) {
                        paramValues[fileIndex] = fileItems.get(fileIndex);
                        fileType = true;
                        fileIndex++;
                    }
                }
                if (!fileType){
                    int i = 0;
                    for(String[] param: paramMap.values()){
                        if (param.length > 1){ // 如果key对应了多个value
                            paramValues[i++] = Arrays.toString(param);
                        }else{ // 如果key 只有1个value
                            paramValues[i++] = param[0];
                        }
                    }
                }
                break;
            case "GET":
                int i = 0;
                for(String[] param: paramMap.values()){
                    if (param.length > 1){ // 如果key对应了多个value
                        paramValues[i++] = Arrays.toString(param);
                    }else{ // 如果key 只有1个value
                        paramValues[i++] = param[0];
                    }
                }
                break;
        }

        try {
            Object res = method.invoke(mapping.getCla(), paramValues);
            switch (type) {
                case Text:
                    // Json 格式
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(res);
                    resp.setContentType("text/html;charset=utf-8");
                    //把方法的执行结果以流的方式返回给前台
                    resp.getWriter().write(json);
                    break;
                case View:
                    DJModelView modelView = (DJModelView) res;

                    for(Map.Entry<String, Object> entry : modelView.getModelMap().entrySet()){
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }

                    req.getRequestDispatcher("/WEB-INF/" + modelView.getView() +".jsp").forward(req, resp);
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
