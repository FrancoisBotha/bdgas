package io.francoisbotha.bdgasadmin.services;

import io.francoisbotha.bdgasadmin.domain.dto.SjsJobDto;
import io.francoisbotha.bdgasadmin.domain.dto.JobDto;
import io.francoisbotha.bdgasadmin.domain.dto.SjsJobResultDto;
import io.francoisbotha.bdgasadmin.domain.dto.SparkJobDto;
import io.francoisbotha.bdgasadmin.error.SjsException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public JobDto runJob(JobDto jobDto, List<String> taskParams) throws RestClientException, SjsException {

        final String uri = endPointService.getSjsJobsEP();

        try {

            // Query parameters
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri)
                                // Add query parameter
                                .queryParam("appName", jobDto.getConfigAppName())
                                .queryParam("classPath", jobDto.getConfigClassPath())
                                .queryParam("context", jobDto.getConfigContext())
                                .queryParam("sync", jobDto.getConfigSync())
                                .queryParam("timeout", jobDto.getConfigTimeout());


            if (taskParams == null
                || taskParams.isEmpty()) {
                log.info("TASK PARAMS IS EMPTY");
            }

            //Form Parameters
//          MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            Map<String,String> map = new HashMap<String,String>();
            map.put("fileFullPath", taskParams.get(0));

            //Headers
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

            // Request entity
            HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(map, headers);


            log.info("++++++++++++++++++++++++++++++BEFORE CALL...");
            log.info(taskParams.toString());

            ResponseEntity<SjsJobResultDto> restResult
                    = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, SjsJobResultDto.class);

            log.info(restResult.getStatusCode().toString());

            log.info(restResult.getBody().toString());
            log.info("++++++++++++++++++++++++++++++++++++AFTER CALL...");


            SjsJobResultDto sjsJobResultDto = restResult.getBody();

            JobDto returnJobDto = new JobDto();

            returnJobDto.setConfigAppName(jobDto.getConfigAppName());
            returnJobDto.setConfigClassPath(jobDto.getConfigClassPath());
            returnJobDto.setConfigContext(jobDto.getConfigContext());
            returnJobDto.setConfigSync(jobDto.getConfigSync());
            returnJobDto.setConfigTimeout(jobDto.getConfigTimeout());
            returnJobDto.setSparkJobId(sjsJobResultDto.getJobId());

            returnJobDto.setResult(sjsJobResultDto.getResult().toString());

//            returnJobDto.setSparkStatus(sparkJobDto.getStatus());
//            returnJobDto.setJobStart(sparkJobDto.getStartTime());
//            returnJobDto.setDuration(sparkJobDto.getDuration());

            return returnJobDto;

        } catch (RestClientException ex) {
            String message = "Rest Exception in SJS Service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        } catch (Exception ex) {
            String message = "General Exception in SJS Service (Run Job): " + ex.getMessage();
            log.error(message, ex);
            throw new SjsException(message);
        }
    }

}

//curl -d "input.string = a b c a b see" "<master node>:8090/jobs?appName=wordcount&classPath=io.francoisbotha.sjs.WordCountExampleNewApi&context=testContext&sync=true&timeout=120"