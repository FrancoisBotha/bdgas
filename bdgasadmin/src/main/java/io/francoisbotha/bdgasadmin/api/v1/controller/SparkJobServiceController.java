package io.francoisbotha.bdgasadmin.api.v1.controller;

import io.francoisbotha.bdgasadmin.services.SjsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class SparkJobServiceController {

    @Autowired
    SjsService sjsService;

    /****************
     * JOBS         *
     * **************/
    @RequestMapping(path = "/api/v1/sjs/jobs", method = RequestMethod.GET)
    public List listS3Objects() {
        try {

            return sjsService.listAllJobs();

        } catch (RestClientException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
