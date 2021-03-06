package com.cqu.DJapp.service;

import com.cqu.DJapp.model.Book;
import com.cqu.myspring.annotation.DJService;

import java.util.ArrayList;
import java.util.List;

@DJService
public class DJbookService {

    private List<Book> allBookList = new ArrayList<Book>(){{
        add(new Book(1, "JavaBook1", "Tian Runze"));
        add(new Book(2, "JavaBook2", "Jiang Dadong"));
        add(new Book(3, "JavaBook3", "Cai Jiaxuan"));
    }};
    public List<Book> getAllBooks(){
        return allBookList;
    }
    public Book getBookById(int id){
        for (Book book : allBookList){
            if (book.getId() == id){
                return book;
            }
        }
        return null;
    }
    public void addBook(int id, String title, String author){
        allBookList.add(new Book(id, title, author));
    }


}