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

import org.bdgas.webproxy.domain.dto.LocalDataSourceDto;
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
public class LocalDataSourceService {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    /************
     * GET ALL  *
     ************/
    public List getAll() {

        final String uri = endPointService.getLocalDataSourcesEP();

        ResponseEntity<List<LocalDataSourceDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<LocalDataSourceDto>>() {
                });

        List<LocalDataSourceDto> tasks = response.getBody();

        return tasks;

    }

    /************
     * GET ONE  *
     ************/
    public LocalDataSourceDto getOne(String id) {

        final String uri = endPointService.getLocalDataSourcesEP()
                + "/" + id;

        try {

            LocalDataSourceDto localDataSourceDto  = restTemplate
                    .getForObject(uri , LocalDataSourceDto.class);

            return localDataSourceDto;

        } catch (RestClientException ex) {

            String message = "Failed get service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * CREATE   *
     ************/
    public void create(LocalDataSourceDto localDataSourceDto) throws RestClientException {

        final String uri = endPointService.getLocalDataSourcesEP();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<LocalDataSourceDto> entity = new HttpEntity<LocalDataSourceDto>(localDataSourceDto, headers);

            ResponseEntity<LocalDataSourceDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, LocalDataSourceDto.class);

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

        final String uri = endPointService.getLocalDataSourcesEP()
                + "/" + id;

        try {

            HttpEntity<String> entity = new HttpEntity<String>("", this.getDefaultHeaders());

            ResponseEntity<LocalDataSourceDto> result = restTemplate.exchange(uri, HttpMethod.DELETE, entity, LocalDataSourceDto.class);

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

