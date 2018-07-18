package io.francoisbotha.bdgaswebproxy.services;

import io.francoisbotha.bdgaswebproxy.domain.dto.WorkingPaperDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
public class WorkingPaperService {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    /************
     * GET ALL  *
     ************/
    public List getAll() {

        final String uri = endPointService.getWorkingPaperEP();

        ResponseEntity<List<WorkingPaperDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WorkingPaperDto>>() {
                });

        List<WorkingPaperDto> workingPapers = response.getBody();

        return workingPapers;

    }

    /******************************
     * GET PROJECT WORKINGPAPERS  *
     ******************************/
    public List getProjectWorkingPapers(String Id) {

        final String uri = endPointService.getProjectWorkingPapersEP() + "/" + Id;

        ResponseEntity<List<WorkingPaperDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WorkingPaperDto>>() {
                });

        List<WorkingPaperDto> workingPapers = response.getBody();

        return workingPapers;

    }

    /************
     * GET ONE  *
     ************/
    public WorkingPaperDto getOne(String id) {

        final String uri = endPointService.getWorkingPaperEP()
                + "/" + id;

        try {

            WorkingPaperDto workingPaperDto  = restTemplate
                    .getForObject(uri , WorkingPaperDto.class);

            return workingPaperDto;

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * CREATE   *
     ************/
    public void create(WorkingPaperDto workingPaperDto) throws RestClientException {

        final String uri = endPointService.getWorkingPaperEP();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            log.debug(workingPaperDto.toString());

            HttpEntity<WorkingPaperDto> entity = new HttpEntity<WorkingPaperDto>(workingPaperDto, headers);

            ResponseEntity<WorkingPaperDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, WorkingPaperDto.class);

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * MODIFY   *
     ************/
    public void modify(WorkingPaperDto workingPaperDto) throws RestClientException {

        final String uri = endPointService.getWorkingPaperEP()
                + "/" + workingPaperDto.getId();

        log.debug(uri.toString());

        try {

            HttpEntity<WorkingPaperDto> entity = new HttpEntity<WorkingPaperDto>(workingPaperDto, this.getDefaultHeaders());

            ResponseEntity<WorkingPaperDto> result = restTemplate.exchange(uri, HttpMethod.PATCH, entity, WorkingPaperDto.class);

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * DELETE   *
     ************/
    public void delete(String id) throws RestClientException {

        final String uri = endPointService.getWorkingPaperEP()
                + "/" + id;

        try {

            HttpEntity<String> entity = new HttpEntity<String>("", this.getDefaultHeaders());

            ResponseEntity<WorkingPaperDto> result = restTemplate.exchange(uri, HttpMethod.DELETE, entity, WorkingPaperDto.class);

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return headers;
    }

}
