package io.francoisbotha.bdgasadmin.api.controller;

import io.francoisbotha.bdgasadmin.api.domain.S3SingedUrl;
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
    public S3SingedUrl getData() {
        try {
            S3SingedUrl s3SingedUrl = new S3SingedUrl();
            s3SingedUrl = s3Service.testS3();
            return s3SingedUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}