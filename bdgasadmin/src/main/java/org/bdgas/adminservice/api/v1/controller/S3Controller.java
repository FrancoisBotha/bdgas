package org.bdgas.adminservice.api.v1.controller;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.bdgas.adminservice.domain.model.S3SingedUrl;
import org.bdgas.adminservice.domain.dto.SignRequestDto;
import org.bdgas.adminservice.services.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Slf4j
@CrossOrigin
@RestController
public class S3Controller{

    @Autowired
    S3Service s3Service;

    @RequestMapping(path = "/api/v1/s3/signedurl", method = RequestMethod.POST, consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public S3SingedUrl getSignedUrl(@RequestBody @Valid SignRequestDto signRequestDto) {
        try {
            S3SingedUrl s3SingedUrl = new S3SingedUrl();
            s3SingedUrl = s3Service.getSignedUrl(signRequestDto.getFileName(), signRequestDto.getContentType());
            return s3SingedUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************
     * TEST METHODS *
     * **************/
    @RequestMapping(path = "/api/v1/s3/listbucketobjects", method = RequestMethod.GET)
    public List listS3Objects() {
        try {

            return s3Service.listBucketObjects();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(path = "/api/v1/s3/getobjectmetadata", method = RequestMethod.GET)
    public ObjectMetadata getObjectMetaData() {
        try {

            s3Service.listBucketObjects();
            //TODO: update hard code
            return  s3Service.getObjectMetaData("Test.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
