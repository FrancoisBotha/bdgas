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
package io.francoisbotha.bdgasadmin.services;

import io.francoisbotha.bdgasadmin.domain.dao.TeamUserRepository;
import io.francoisbotha.bdgasadmin.domain.dao.CodeTypeRepository;
import io.francoisbotha.bdgasadmin.domain.dto.TeamUserDto;
import io.francoisbotha.bdgasadmin.domain.model.TeamUser;
import io.francoisbotha.bdgasadmin.domain.model.CodeType;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class TeamUserService {

    @Autowired
    private TeamUserRepository teamUserRepository;

    @Autowired
    CodeTypeService codeTypeService;

    public List getAll() {

        List TeamUsers = new ArrayList();

        Iterable<TeamUser> TeamUsersIt = teamUserRepository.findAll();

        Iterator<TeamUser> iter = TeamUsersIt.iterator();

        while (iter.hasNext()) {
            TeamUsers.add(iter.next());
        }

        return TeamUsers;
    }

    public TeamUser getOne(String id) throws EntityNotFoundException {

        TeamUser teamUser = teamUserRepository.findOneById(id);

        return teamUser;
    }


    public List  getTeamsForUser(String userId) {

        List teamUsers = new ArrayList();

        Iterable<TeamUser> teamUsersIt = teamUserRepository.findAllByUserId(userId);

        Iterator<TeamUser> iter = teamUsersIt.iterator();

        while (iter.hasNext()) {
            teamUsers.add(iter.next());
        }

        return teamUsers;
    }

    public List  getUsersForTeam(String teamId) {

        List teamUsers = new ArrayList();

        Iterable<TeamUser> teamUsersIt = teamUserRepository.findAllByTeamId(teamId);

        Iterator<TeamUser> iter = teamUsersIt.iterator();

        while (iter.hasNext()) {
            teamUsers.add(iter.next());
        }

        return teamUsers;
    }

    public TeamUser create(TeamUser teamUser) {
        return teamUserRepository.save(teamUser);
    }

    public void delete(String id)  throws EntityNotFoundException {

        TeamUser teamUser = teamUserRepository.findOneById(id);

        if(teamUser == null){
            throw new EntityNotFoundException(TeamUser.class, "id", id.toString());
        }

        teamUserRepository.delete(teamUser);
    }
}

