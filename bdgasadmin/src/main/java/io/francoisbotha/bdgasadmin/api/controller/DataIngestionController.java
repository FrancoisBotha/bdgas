package io.francoisbotha.bdgasadmin.api.controller;

import io.francoisbotha.bdgasadmin.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@CrossOrigin
@RestController
public class DataIngestionController{

    @Autowired
    S3Service s3Service;

    @RequestMapping("/data")
    public String getData() {
        try {
            return s3Service.testS3();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}