package io.francoisbotha.bdgasadmin.api.v1.controller;

import io.francoisbotha.bdgasadmin.services.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

sss
@Slf4j
@CrossOrigin
@RestController
public class DataSourceController{

    @Autowired
    S3Service s3Service;

    @RequestMapping(path = "/api/v1/datasource", method = RequestMethod.GET)
    public List listS3Objects() {
        try {

            return s3Service.listBucketObjects();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}