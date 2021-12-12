package com.cqu.DJapp.controller;

import com.cqu.DJapp.model.Book;
import com.cqu.DJapp.model.RestModel;
import com.cqu.DJapp.service.DJbookService;
import com.cqu.myspring.annotation.*;

import java.util.List;
@DJController
@DJRequestMapping("/api")
public class DJrestController {

    @Autowired
    private DJbookService DJbookService;


    @DJRequestMapping(value = "/book", method = "GET")
    @ResponseBody
    public Book getBook(@DJRequestParam("id") String sid){
        int id = Integer.parseInt(sid);
        return DJbookService.getBookById(id);
    }

    @DJRequestMapping(value = "/book", method = "POST")
    @ResponseBody
    public RestModel addBook(@DJRequestParam("id") String id, @DJRequestParam("title") String title, @DJRequestParam("author") String author){
        DJbookService.addBook(Integer.parseInt(id), title, author);
        return new RestModel(200, "ok", null);
    }

    @DJRequestMapping(value = "/book2", method = "GET")
    @ResponseBody
    public RestModel getBookMsg(@DJRequestParam("id") String sid){
        int id = Integer.parseInt(sid);
        Book book = DJbookService.getBookById(id);
        return new RestModel(200, "ok", book);
    }

    @DJRequestMapping(value = "/book/all", method = "GET")
    @ResponseBody
    public List<Book> getAllBooks(){
        return DJbookService.getAllBooks();
    }


}
