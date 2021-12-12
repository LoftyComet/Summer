package com.cqu.DJapp.controller;

import com.cqu.DJapp.model.Book;
import com.cqu.DJapp.service.DJappService;
import com.cqu.DJapp.service.DJbookService;
import com.cqu.myspring.annotation.*;
import com.cqu.myspring.model.DJModelView;
import org.apache.commons.fileupload.FileItem;


import java.util.List;


@DJController
@DJRequestMapping("/app")
public class DJappcontroller {


    @Autowired
    private DJappService DJappService;
    @Autowired
    private DJbookService DJbookService;

    @DJRequestMapping(value = "/bookpage", method="POST")
    @ResponseView
    public DJModelView addBook(@DJRequestParam("id") String id, @DJRequestParam("title") String title, @DJRequestParam("author") String author){
        DJModelView mv = new DJModelView();
        DJbookService.addBook(Integer.parseInt(id), title, author);
        mv.setView("bookinfo");
        mv.addModel("bookList", DJbookService.getAllBooks());
        return mv;
    }

    @DJRequestMapping(value = "/bookpage", method = "GET")
    @ResponseView
    public DJModelView showBookPage(){
        DJModelView modelView = new DJModelView();
        List<Book> bookList = DJbookService.getAllBooks();
        modelView.setView("bookinfo");
        modelView.addModel("bookList", bookList);
        return modelView;
    }



    @DJRequestMapping(value = "/upload", method = "GET")
    @ResponseView
    public DJModelView upload_page() {
        DJModelView mv = new DJModelView();
        mv.setView("upload");
        return mv;
    }


    @DJRequestMapping(value = "/upload", method = "POST")
    @ResponseView

    public DJModelView upload(@DJRequestParam("file") FileItem source) {

        String path = "D:/code/JAVAEE_MVCWebFramework/temp/"; // 假定这里路径是固定的

        DJModelView mv = new DJModelView();
        mv.setView("upload_result");
        if (DJappService.uploadFile(source, path)){
            mv.addModel("info", "Successfully upload");
        }else{
            mv.addModel("info", "Failure to upload");
        }
        return mv;
    }
}
