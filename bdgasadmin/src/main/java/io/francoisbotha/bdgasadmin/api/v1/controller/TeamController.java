package io.francoisbotha.bdgasadmin.api.v1.controller;

import io.francoisbotha.bdgasadmin.domain.dto.TeamDto;
import io.francoisbotha.bdgasadmin.domain.model.Team;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.services.TeamService;
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
