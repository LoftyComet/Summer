package com.cqu.DJapp.service;

import com.cqu.myspring.annotation.DJService;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
@DJService
public class DJappService {


    public boolean uploadFile(FileItem source, String path) {
        String fileName = source.getName();
        File dest = new File(path + fileName);
        try {
            source.write(dest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
