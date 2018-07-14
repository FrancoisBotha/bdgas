package io.francoisbotha.bdgasadmin.api.v1.controller;


import io.francoisbotha.bdgasadmin.domain.dao.TeamRepository;
import io.francoisbotha.bdgasadmin.domain.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @RequestMapping(value = "/api/v1/team", method = RequestMethod.GET)
    public List getTeams (Model model) {

        Iterable<Team> teamsIt = teamRepository.findAll();

        Iterator<Team> iter = teamsIt.iterator();
        List teams = new ArrayList();
        while (iter.hasNext()) {
            teams.add(iter.next());
        }

        return teams;

    }

    /************
     * POST     *
     ************/
    @RequestMapping(value = "/api/v1/team", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Team AddTeam(@RequestBody Team team) {
        return teamRepository.save(team);
    }

}
