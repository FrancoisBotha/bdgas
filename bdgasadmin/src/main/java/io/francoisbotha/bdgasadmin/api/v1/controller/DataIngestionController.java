package io.francoisbotha.bdgasadmin.api.v1.controller;

import io.francoisbotha.bdgasadmin.domain.model.S3SingedUrl;
import io.francoisbotha.bdgasadmin.services.S3Service;
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

    @RequestMapping(path = "/api/v1/getsignedurl", method = RequestMethod.POST)
    public S3SingedUrl getData(@RequestParam String fileName, @RequestParam String contentType) {
        try {
            S3SingedUrl s3SingedUrl = new S3SingedUrl();
            s3SingedUrl = s3Service.getSignedUrl(fileName, contentType);
            log.debug(s3SingedUrl.getUrl());
            return s3SingedUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}