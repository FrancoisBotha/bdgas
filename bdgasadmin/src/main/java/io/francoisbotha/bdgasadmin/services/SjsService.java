package io.francoisbotha.bdgasadmin.services;

import io.francoisbotha.bdgasadmin.domain.dto.SjsJobDto;
import io.francoisbotha.bdgasadmin.domain.dto.JobDto;
import io.francoisbotha.bdgasadmin.domain.dto.SjsJobResultDto;
import io.francoisbotha.bdgasadmin.domain.dto.SparkJobDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    public JobDto runJob(JobDto jobDto) throws RestClientException {

        final String uri = endPointService.getSjsJobsEP();

        log.debug(uri.toString());

        try {

            // Query parameters
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri)
                                // Add query parameter
                                .queryParam("appName", jobDto.getConfigAppName())
                                .queryParam("classPath", jobDto.getConfigClassPath())
                                .queryParam("context", jobDto.getConfigContext())
                                .queryParam("sync", jobDto.getConfigSync())
                                .queryParam("timeout", jobDto.getConfigTimeout());

            //Form Parameters
            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("fileName", "dummy file name");

            //Headers
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            // Request entity
            HttpEntity<MultiValueMap<String, String>> entity= new HttpEntity<MultiValueMap<String, String>>(map, headers);

            ResponseEntity<SjsJobResultDto> result
                    = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, SjsJobResultDto.class);

            SjsJobResultDto sjsJobResultDto = result.getBody();

            JobDto returnJobDto = new JobDto();
            returnJobDto.setConfigAppName(jobDto.getConfigAppName());
            returnJobDto.setConfigClassPath(jobDto.getConfigClassPath());
            returnJobDto.setConfigContext(jobDto.getConfigContext());
            returnJobDto.setConfigSync(jobDto.getConfigSync());
            returnJobDto.setConfigTimeout(jobDto.getConfigTimeout());
            returnJobDto.setSparkJobId(sjsJobResultDto.getJobId());
            returnJobDto.setResult(sjsJobResultDto.getResult());

//            returnJobDto.setSparkStatus(sparkJobDto.getStatus());
//            returnJobDto.setJobStart(sparkJobDto.getStartTime());
//            returnJobDto.setDuration(sparkJobDto.getDuration());

            return returnJobDto;

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

}

//curl -d "input.string = a b c a b see" "<master node>:8090/jobs?appName=wordcount&classPath=io.francoisbotha.sjs.WordCountExampleNewApi&context=testContext&sync=true&timeout=120"