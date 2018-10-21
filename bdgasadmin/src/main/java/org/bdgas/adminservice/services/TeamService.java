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
package org.bdgas.adminservice.services;

import org.bdgas.adminservice.domain.dao.TeamRepository;
import org.bdgas.adminservice.domain.model.TeamUser;
import org.bdgas.adminservice.domain.model.User;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.domain.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    TeamUserService teamUserService;

    @Autowired
    UserService userService;

    public Team getTeam(String id) throws EntityNotFoundException {
        Team team = teamRepository.findOneById(id);
        if(team == null){
            throw new EntityNotFoundException(Team.class, "id", id.toString());
        }
        return team;
    }

    public List getAll() {

        List teams = new ArrayList();

        Iterable<Team> teamsIt = teamRepository.findAll();

        Iterator<Team> iter = teamsIt.iterator();

        while (iter.hasNext()) {
            teams.add(iter.next());
        }

        return teams;
    }

    public List getAll(String id) throws EntityNotFoundException  {

        List teams = new ArrayList();

        Iterable<Team> teamsIt = teamRepository.findAllById(id);

        Iterator<Team> iter = teamsIt.iterator();

        while (iter.hasNext()) {
            teams.add(iter.next());
        }

        if(teams.isEmpty()
                || teams.get(0) == null){
            throw new EntityNotFoundException(Team.class, "id", id.toString());
        }

        return teams;
    }

    public Team getOne(String id) throws EntityNotFoundException {


        Team team = teamRepository.findOneById(id);

        return team;
    }

    public List getTeamsForUser(String id) throws EntityNotFoundException  {

        List teams = new ArrayList();

        //first, get user id linked to userAuthId
        User user = userService.getOneByAuthId(id);

        //Next, get all teams user is linked to:
        Iterable<TeamUser> teamUsersIt =  teamUserService.getTeamsForUser(user.getId());

        Iterator<TeamUser> iterTeamUsers = teamUsersIt.iterator();

        while (iterTeamUsers.hasNext()) {
            TeamUser teamUser = iterTeamUsers.next();
            Team team = teamRepository.findOneById(teamUser.getTeamId());
            teams.add(team);
        }

        if(teams.isEmpty()
                || teams.get(0) == null){
            throw new EntityNotFoundException(Team.class, "id", id.toString());
        }

        return teams;
    }


    public Team create(Team team) {
        return teamRepository.save(team);
    }

    public void delete(String id)  throws EntityNotFoundException {

        Team team = teamRepository.findOneById(id);

        teamRepository.delete(team);
    }

}



