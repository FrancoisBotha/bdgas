package io.francoisbotha.bdgasadmin.services;

import io.francoisbotha.bdgasadmin.domain.dto.SjsJobDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class SjsService {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    /************
     * JOBS     *
     ************/
    public List listAllJobs() {

        final String uri = endPointService.getSjsJobsEP();

        try {

            ResponseEntity<List<SjsJobDto>> response
                    = restTemplate.exchange(uri,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<SjsJobDto>>() {
                    });

            List<SjsJobDto> dataSources = response.getBody();

            return dataSources;

        } catch (RestClientException ex) {

            String message = "Failed to get service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }

    }

    public SjsJobDto getJob(String jobId) {

        final String uri = endPointService.getSjsJobsEP()
                            + "/" + jobId;

        try {

            SjsJobDto sjsJobDto  = restTemplate
                    .getForObject(uri , SjsJobDto.class);

            return sjsJobDto;

        } catch (RestClientException ex) {

            String message = "Failed to get service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }

    }

    public void runJob(DataSourceDto dataSourceDto) throws RestClientException {

        final String uri = endPointService.getSjsJobsEP();

        log.debug(uri.toString());

        try {

            // Query parameters
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                                // Add query parameter
                                .queryParam("firstName", "Mark")
                                .queryParam("lastName", "Watney");

            HttpEntity<DataSourceDto> entity
                    = new HttpEntity<DataSourceDto>(dataSourceDto, this.getDefaultHeaders());

            ResponseEntity<DataSourceDto> result
                    = restTemplate.exchange(uri, HttpMethod.POST, entity, DataSourceDto.class);

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

}

//curl -d "input.string = a b c a b see" "<master node>:8090/jobs?appName=wordcount&classPath=io.francoisbotha.sjs.WordCountExampleNewApi&context=testContext&sync=true&timeout=120"