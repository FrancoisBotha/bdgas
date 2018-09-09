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

import io.francoisbotha.bdgaswebproxy.domain.dto.WpLineDto;
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
public class WpLineService {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    /************
     * GET ALL  *
     ************/
    public List getAll() {

        final String uri = endPointService.getWpLineEP();

        ResponseEntity<List<WpLineDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WpLineDto>>() {
                });

        List<WpLineDto> wpLines = response.getBody();

        return wpLines;

    }

    /***************************
     * GET WORKINGPAPER Lines  *
     ***************************/
    public List getProjectWorkingPapers(String Id) {

        final String uri = endPointService.getWorkingPaperLinesEP() + "/" + Id;

        ResponseEntity<List<WpLineDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<WpLineDto>>() {
                });

        List<WpLineDto> wpLines = response.getBody();

        return wpLines;

    }

    /************
     * GET ONE  *
     ************/
    public WpLineDto getOne(String id) {

        final String uri = endPointService.getWpLineEP()
                + "/" + id;

        try {

            WpLineDto wpLineDto  = restTemplate
                    .getForObject(uri , WpLineDto.class);

            return wpLineDto;

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * CREATE   *
     ************/
    public WpLineDto create(WpLineDto workingPaperDto) throws RestClientException {

        final String uri = endPointService.getWpLineEP();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            log.debug(workingPaperDto.toString());

            HttpEntity<WpLineDto> entity = new HttpEntity<WpLineDto>(workingPaperDto, headers);

            WpLineDto wpLineDtoReturn = restTemplate.postForObject(uri, entity, WpLineDto.class);
            return wpLineDtoReturn;

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * MODIFY   *
     ************/
    public void modify(WpLineDto workingPaperDto) throws RestClientException {

        final String uri = endPointService.getWpLineEP()
                + "/" + workingPaperDto.getId();

        try {

            HttpEntity<WpLineDto> entity = new HttpEntity<WpLineDto>(workingPaperDto, this.getDefaultHeaders());

            ResponseEntity<WpLineDto> result = restTemplate.exchange(uri, HttpMethod.PATCH, entity, WpLineDto.class);

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

        final String uri = endPointService.getWpLineEP()
                + "/" + id;

        try {

            HttpEntity<String> entity = new HttpEntity<String>("", this.getDefaultHeaders());

            ResponseEntity<WpLineDto> result = restTemplate.exchange(uri, HttpMethod.DELETE, entity, WpLineDto.class);

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
