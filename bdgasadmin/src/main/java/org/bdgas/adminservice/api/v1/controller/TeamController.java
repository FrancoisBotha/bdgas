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
package org.bdgas.adminservice.api.v1.controller;

import org.bdgas.adminservice.domain.dto.TeamDto;
import org.bdgas.adminservice.domain.model.Team;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.services.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/team", method = RequestMethod.GET)
    public List getTeams () throws EntityNotFoundException {

        log.info("Get Teams");

        return teamService.getAll();

    }

    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/team/{id}", method = RequestMethod.GET)
    public Team getTeam (@PathVariable("id") String id) throws EntityNotFoundException {

        return teamService.getOne(id);

    }

    /********************
     * GET FOR USER     *
     ********************/
    @RequestMapping(value = "/api/v1/team/user/{id}", method = RequestMethod.GET)
    public List getCodesForType (@PathVariable("id") String id) throws EntityNotFoundException {

        return teamService.getTeamsForUser(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/team", method = RequestMethod.POST, consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Team AddTeam(@RequestBody @Valid TeamDto teamDto )  {
        Team team = new Team();
        team.setName(teamDto.getName());
        return teamService.create(team);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/team/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeam(@PathVariable("id") String id) throws EntityNotFoundException  {
        teamService.delete(id);
    }

}
