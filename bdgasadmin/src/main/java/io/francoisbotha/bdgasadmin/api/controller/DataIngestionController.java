package io.francoisbotha.bdgasadmin.api.controller;

import io.francoisbotha.bdgasadmin.api.domain.S3SingedUrl;
import io.francoisbotha.bdgasadmin.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Slf4j
@CrossOrigin
@RestController
public class DataIngestionController{

    @Autowired
    S3Service s3Service;

    @RequestMapping(path = "/data", method = RequestMethod.POST)
    public S3SingedUrl getData(@RequestParam String fileName, @RequestParam String contentType) {
        try {
            S3SingedUrl s3SingedUrl = new S3SingedUrl();
            s3SingedUrl = s3Service.testS3(fileName, contentType);
            log.debug(s3SingedUrl.getUrl());
            return s3SingedUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}