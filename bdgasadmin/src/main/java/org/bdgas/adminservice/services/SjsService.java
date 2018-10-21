package org.bdgas.adminservice.services;

import org.bdgas.adminservice.domain.dto.SjsJobDto;
import org.bdgas.adminservice.domain.dto.JobDto;
import org.bdgas.adminservice.domain.dto.SjsJobResultDto;
import org.bdgas.adminservice.error.SjsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.*;

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

        log.debug("JobId");
        log.debug(jobId);

        final String uri = endPointService.getSjsJobsEP()
                            + "/" + jobId;

        try {

            //Headers
            HttpHeaders headers = new HttpHeaders();
            Map<String,String> map = new HashMap<String,String>();

            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            // Request entity
            HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(map, headers);

            ResponseEntity<SjsJobDto> restResult
                    = restTemplate.exchange(uri, HttpMethod.GET, entity, SjsJobDto.class);


            SjsJobDto sjsJobDto = restResult.getBody();

            return sjsJobDto;

        } catch (RestClientException ex) {

            String message = "Failed to get service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }

    }

    public JobDto runJob(JobDto jobDto, List<String> taskParams) throws HttpStatusCodeException, RestClientException, SjsException {

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

            //*****************************
            //* VALIDATE INPUT PARAMETERS *
            //*****************************
            if (taskParams.isEmpty()) {
                String message = "Invalid task parameters supplied.";
                log.error(message);
                throw new SjsException(message);
            }

            //Form Parameters
//          MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            Map<String,String> map = new HashMap<String,String>();
            map.put("param0", taskParams.get(0));
            map.put("param1", taskParams.get(1));
            map.put("param2", taskParams.get(2));

            //Headers
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            // Request entity
            HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(map, headers);


            log.info("++++++++++++++++++++++++++++++BEFORE CALL...");
            log.info(taskParams.toString());

            ResponseEntity<SjsJobResultDto> restResult
                    = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, SjsJobResultDto.class);

            log.info("status Code...");
            log.info(restResult.getStatusCode().toString());

            log.info("Body..");
            log.info(restResult.getBody().toString());
            log.info("++++++++++++++++++++++++++++++++++++AFTER CALL...");


            SjsJobResultDto sjsJobResultDto = restResult.getBody();

            if (sjsJobResultDto.getStatus() != null
                 && sjsJobResultDto.getStatus().equals("ERROR")) {
                throw new SjsException("Error executing job on cluster");
            }

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

        }
        catch (HttpStatusCodeException ex) {
            int statusCode = ex.getStatusCode().value();
            log.error("SjsService: Run Job: HttpStatusCodeException");
            log.error(ex.getResponseBodyAsString());
            throw ex;
        }
        catch (RestClientException ex) {
            String message = ex.getMessage();
            log.error("SjsService RestClientException");
            log.error(message, ex);
            throw new SjsException(message);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log.error("SjsService Exception");
            log.error(message, ex);
            throw new SjsException(message);
        }
    }

}

//curl -d "input.string = a b c a b see" "<master node>:8090/jobs?appName=wordcount&classPath=io.francoisbotha.sjs.WordCountExampleNewApi&context=testContext&sync=true&timeout=120"