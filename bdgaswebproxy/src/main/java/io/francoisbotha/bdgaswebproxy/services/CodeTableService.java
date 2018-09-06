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

import io.francoisbotha.bdgaswebproxy.domain.dto.CodeTableDto;
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
public class CodeTableService {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    /************
     * GET ALL  *
     ************/
    public List getAll() {

        final String uri = endPointService.getCodeTableEP();

        ResponseEntity<List<CodeTableDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CodeTableDto>>() {
                });

        List<CodeTableDto> codeTables = response.getBody();

        return codeTables;

    }

    /***************************
     * GET CODE TABLE FOR TYPE *
     ***************************/
    public List getCodeTablesForType(String Id) {

        final String uri = endPointService.getCodeTypeTablesEP() + "/" + Id;

        ResponseEntity<List<CodeTableDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CodeTableDto>>() {
                });

        List<CodeTableDto> dataSources = response.getBody();

        return dataSources;

    }


    /************
     * GET ONE  *
     ************/
    public CodeTableDto getOne(String id) {

        final String uri = endPointService.getCodeTableEP()
                + "/" + id;

        try {

            CodeTableDto codeTableDto  = restTemplate
                    .getForObject(uri , CodeTableDto.class);

            return codeTableDto;

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * CREATE   *
     ************/
    public void create(CodeTableDto codeTableDto) throws RestClientException {

        final String uri = endPointService.getCodeTableEP();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<CodeTableDto> entity = new HttpEntity<CodeTableDto>(codeTableDto, headers);

            ResponseEntity<CodeTableDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, CodeTableDto.class);

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * MODIFY   *
     ************/
    public void modify(CodeTableDto codeTableDto) throws RestClientException {

        final String uri = endPointService.getCodeTableEP()
                + "/" + codeTableDto.getId();

        try {

            HttpEntity<CodeTableDto> entity = new HttpEntity<CodeTableDto>(codeTableDto, this.getDefaultHeaders());

            ResponseEntity<CodeTableDto> result = restTemplate.exchange(uri, HttpMethod.PATCH, entity, CodeTableDto.class);

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

        final String uri = endPointService.getCodeTableEP()
                + "/" + id;

        try {

            HttpEntity<String> entity = new HttpEntity<String>("", this.getDefaultHeaders());

            ResponseEntity<CodeTableDto> result = restTemplate.exchange(uri, HttpMethod.DELETE, entity, CodeTableDto.class);

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
