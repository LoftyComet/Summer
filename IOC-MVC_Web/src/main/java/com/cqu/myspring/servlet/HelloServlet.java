package com.cqu.myspring.servlet;

import com.cqu.service.HelloService;
import com.cqu.service.impl.HelloServiceImpl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/hello")
public class HelloServlet extends HttpServlet {
    // 注入service
    private HelloService helloService = new HelloServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(this.helloService.findAll().toString());
    }
}