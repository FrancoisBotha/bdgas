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
package io.francoisbotha.bdgasadmin.api.v1.controller;

import io.francoisbotha.bdgasadmin.domain.dto.TeamDto;
import io.francoisbotha.bdgasadmin.domain.dto.TeamUserDto;
import io.francoisbotha.bdgasadmin.domain.model.Team;
import io.francoisbotha.bdgasadmin.domain.model.TeamUser;
import io.francoisbotha.bdgasadmin.domain.model.User;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.services.TeamService;
import io.francoisbotha.bdgasadmin.services.TeamUserService;
import io.francoisbotha.bdgasadmin.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class TeamUserController {

    @Autowired
    TeamUserService teamUserService;

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;


    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/teamuser", method = RequestMethod.GET)
    public List getTeamUsers () throws EntityNotFoundException {

        return teamUserService.getAll();

    }

    /************
     * GET One  *
     ************/
    @RequestMapping(value = "/api/v1/teamuser/{id}", method = RequestMethod.GET)
    public TeamUser getTeamUser (@PathVariable("id") String id) throws EntityNotFoundException {

        return teamUserService.getOne(id);

    }

    /********************
     * GET FOR USER ID  *
     ********************/
    @RequestMapping(value = "/api/v1/teamuser/user/{id}", method = RequestMethod.GET)
    public List getTeamsForUser (@PathVariable("id") String id) throws EntityNotFoundException {

        return teamUserService.getTeamsForUser(id);

    }

    /********************
     * GET FOR USER ID  *
     ********************/
    @RequestMapping(value = "/api/v1/teamuser/team/{id}", method = RequestMethod.GET)
    public List getUsersForTeam (@PathVariable("id") String id) throws EntityNotFoundException {

        return teamUserService.getUsersForTeam(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/teamuser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TeamUser AddTeamUser(@RequestBody @Valid TeamUserDto teamUserDto ) throws EntityNotFoundException {
        TeamUser teamUser = new TeamUser();
        teamUser.setTeamId(teamUserDto.getTeamId());
        teamUser.setUserId(teamUserDto.getUserId());

        Team team = teamService.getOne(teamUserDto.getTeamId());
        teamUser.setTeamName(team.getName());

        User user = userService.getOne(teamUserDto.getUserId());
        teamUser.setUserAuthId(user.getAuthId());
        teamUser.setUserFirstName(user.getFirstName());
        teamUser.setUserLastName(user.getLastName());

        return teamUserService.create(teamUser);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/teamuser/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeamUser(@PathVariable("id") String id) throws EntityNotFoundException  {
        teamUserService.delete(id);
    }

}
