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

import org.bdgas.webproxy.domain.dto.DataSourceDto;
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
public class DataSourceService {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    /************
     * GET ALL  *
     ************/
    public List getAll() {

        final String uri = endPointService.getDataSourceEP();

        ResponseEntity<List<DataSourceDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<DataSourceDto>>() {
                });

        List<DataSourceDto> dataSources = response.getBody();

        return dataSources;

    }

    /*************************
     * GET TEAM DATA SOURCES *
     *************************/
    public List getTeamDataSources(String Id) {

        final String uri = endPointService.getTeamDataSourcesEP() + "/" + Id;

        ResponseEntity<List<DataSourceDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<DataSourceDto>>() {
                });

        List<DataSourceDto> dataSources = response.getBody();

        return dataSources;

    }

    /************
     * GET ONE  *
     ************/
    public DataSourceDto getOne(String id) {

        final String uri = endPointService.getDataSourceEP()
                + "/" + id;

        try {

            DataSourceDto dataSourceDto  = restTemplate
                    .getForObject(uri , DataSourceDto.class);

            return dataSourceDto;

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }



    /************
     * CREATE   *
     ************/
    public DataSourceDto create(DataSourceDto dataSourceDto) throws RestClientException {

        final String uri = endPointService.getDataSourceEP();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<DataSourceDto> entity = new HttpEntity<DataSourceDto>(dataSourceDto, headers);

            DataSourceDto dataSourceDtoReturn = restTemplate.postForObject(uri, entity, DataSourceDto.class);
            return dataSourceDtoReturn;

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * MODIFY   *
     ************/
    public void modify(DataSourceDto dataSourceDto) throws RestClientException {

        final String uri = endPointService.getDataSourceEP()
                + "/" + dataSourceDto.getId();

        log.debug(uri.toString());

        try {

            HttpEntity<DataSourceDto> entity = new HttpEntity<DataSourceDto>(dataSourceDto, this.getDefaultHeaders());

            ResponseEntity<DataSourceDto> result = restTemplate.exchange(uri, HttpMethod.PATCH, entity, DataSourceDto.class);

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

        final String uri = endPointService.getDataSourceEP()
                + "/" + id;

        try {

            HttpEntity<String> entity = new HttpEntity<String>("", this.getDefaultHeaders());

            ResponseEntity<DataSourceDto> result = restTemplate.exchange(uri, HttpMethod.DELETE, entity, DataSourceDto.class);

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