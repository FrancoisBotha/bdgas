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
package io.francoisbotha.bdgaswebproxy.services;

import io.francoisbotha.bdgaswebproxy.domain.dto.ProjectDto;
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
public class ProjectService {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    /************
     * GET ALL  *
     ************/
    public List getAll() {

        final String uri = endPointService.getProjectEP();

        ResponseEntity<List<ProjectDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ProjectDto>>() {
                });

        List<ProjectDto> projects = response.getBody();

        return projects;

    }

    /*********************
     * GET TEAM PROJECTS *
     *********************/
    public List getTeamProjects(String Id) {

        final String uri = endPointService.getTeamProjectsEP() + "/" + Id;

        ResponseEntity<List<ProjectDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ProjectDto>>() {
                });

        List<ProjectDto> projects = response.getBody();

        return projects;

    }

    /************
     * GET ONE  *
     ************/
    public ProjectDto getOne(String id) {

        final String uri = endPointService.getProjectEP()
                + "/" + id;

        try {

            ProjectDto projectDto  = restTemplate
                    .getForObject(uri , ProjectDto.class);

            return projectDto;

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }



    /************
     * CREATE   *
     ************/
    public void create(ProjectDto projectDto) throws RestClientException {

        final String uri = endPointService.getProjectEP();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            log.debug(projectDto.toString());

            HttpEntity<ProjectDto> entity = new HttpEntity<ProjectDto>(projectDto, headers);

            ResponseEntity<ProjectDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, ProjectDto.class);

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * MODIFY   *
     ************/
    public void modify(ProjectDto projectDto) throws RestClientException {

        final String uri = endPointService.getProjectEP()
                + "/" + projectDto.getId();

        log.debug(uri.toString());

        try {

            HttpEntity<ProjectDto> entity = new HttpEntity<ProjectDto>(projectDto, this.getDefaultHeaders());

            ResponseEntity<ProjectDto> result = restTemplate.exchange(uri, HttpMethod.PATCH, entity, ProjectDto.class);

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

        final String uri = endPointService.getProjectEP()
                + "/" + id;

        try {

            HttpEntity<String> entity = new HttpEntity<String>("", this.getDefaultHeaders());

            ResponseEntity<ProjectDto> result = restTemplate.exchange(uri, HttpMethod.DELETE, entity, ProjectDto.class);

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
