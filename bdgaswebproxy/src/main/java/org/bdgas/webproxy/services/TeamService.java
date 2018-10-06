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

import org.bdgas.webproxy.domain.dto.TeamDto;
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
public class TeamService {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    /************
     * GET ALL  *
     ************/
    public List getAll() {

        final String uri = endPointService.getTeamEP();

        ResponseEntity<List<TeamDto>> response
                = restTemplate.exchange(uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<TeamDto>>() {
                });

        List<TeamDto> teams = response.getBody();

        return teams;

    }

    /************
     * GET ONE  *
     ************/
    public TeamDto getOne(String id) {

        final String uri = endPointService.getTeamEP()
                + "/" + id;

        try {

            TeamDto teamDto  = restTemplate
                    .getForObject(uri , TeamDto.class);

            return teamDto;

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * CREATE   *
     ************/
    public void create(TeamDto teamDto) throws RestClientException {

        final String uri = endPointService.getTeamEP();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            log.debug(teamDto.toString());

            HttpEntity<TeamDto> entity = new HttpEntity<TeamDto>(teamDto, headers);

            ResponseEntity<TeamDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, TeamDto.class);

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    /************
     * MODIFY   *
     ************/
    public void modify(TeamDto teamDto) throws RestClientException {

        final String uri = endPointService.getTeamEP()
                + "/" + teamDto.getId();

        log.debug(uri.toString());

        try {

            HttpEntity<TeamDto> entity = new HttpEntity<TeamDto>(teamDto, this.getDefaultHeaders());

            ResponseEntity<TeamDto> result = restTemplate.exchange(uri, HttpMethod.PATCH, entity, TeamDto.class);

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

        final String uri = endPointService.getTeamEP()
                + "/" + id;

        try {

            HttpEntity<String> entity = new HttpEntity<String>("", this.getDefaultHeaders());

            ResponseEntity<TeamDto> result = restTemplate.exchange(uri, HttpMethod.DELETE, entity, TeamDto.class);

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
