/*****************************************************************************
 * Copyright 2018 Francois Botha                                             *
 *                                                                           *
 * Licensed under the Apache License, Version 2.0 (the "License");           *
 * you may not use this file except in compliance with the License.          *
 * You may obtain a copy of the License at                                   *
 *                                                                           *
 *  http://www.apache.org/licenses/LICENSE-2.0                               *
 *                                                                           *
 * Unless required by applicable law or agreed to in writing, software       *
 * distributed under the License is distributed on an "AS IS" BASIS,         *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 * See the License for the specific language governing permissions and       *
 * limitations under the License.                                            *
 *                                                                           *
 *****************************************************************************/
package org.bdgas.webproxy.services;

import org.bdgas.webproxy.domain.dto.TaskDto;
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
public class TaskService  {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    /************
     * GET ALL  *
     ************/
    public List getAll() {

        final String uri = endPointService.getTaskEP();

        ResponseEntity<List<TaskDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<TaskDto>>() {
                });

        List<TaskDto> tasks = response.getBody();

        return tasks;

    }

    /************
     * GET ONE  *
     ************/
    public TaskDto getOne(String id) {

        final String uri = endPointService.getTaskEP()
                + "/" + id;

        try {

            TaskDto taskDto  = restTemplate
                    .getForObject(uri , TaskDto.class);

            return taskDto;

        } catch (RestClientException ex) {

            String message = "Failed get service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * CREATE   *
     ************/
    public void create(TaskDto taskDto) throws RestClientException {

        final String uri = endPointService.getTaskEP();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<TaskDto> entity = new HttpEntity<TaskDto>(taskDto, headers);

            ResponseEntity<TaskDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, TaskDto.class);

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * MODIFY   *
     ************/
    public void modify(TaskDto taskDto) throws RestClientException {

        final String uri = endPointService.getTaskEP()
                + "/" + taskDto.getId();

        try {

            HttpEntity<TaskDto> entity = new HttpEntity<TaskDto>(taskDto, this.getDefaultHeaders());

            ResponseEntity<TaskDto> result = restTemplate.exchange(uri, HttpMethod.PATCH, entity, TaskDto.class);

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

        final String uri = endPointService.getTaskEP()
                + "/" + id;

        try {

            HttpEntity<String> entity = new HttpEntity<String>("", this.getDefaultHeaders());

            ResponseEntity<TaskDto> result = restTemplate.exchange(uri, HttpMethod.DELETE, entity, TaskDto.class);

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
