package io.francoisbotha.bdgasadmin.api.v1.controller;

import io.francoisbotha.bdgasadmin.services.SjsService;
import io.francoisbotha.bdgasadmin.domain.dto.SjsJobDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
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
    public List listJobs() {
        try {

            return sjsService.listAllJobs();

        } catch (RestClientException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(path = "/api/v1/sjs/jobs/{jobId}", method = RequestMethod.GET)
    public SjsJobDto getJob(ModelMap model, @PathVariable("jobId") String jobId) {

        try {

            return sjsService.getJob(jobId);

        } catch (RestClientException ex) {
            ex.printStackTrace();
        }
        return null;

    }

}
