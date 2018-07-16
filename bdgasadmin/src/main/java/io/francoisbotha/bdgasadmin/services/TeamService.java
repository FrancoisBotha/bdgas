package io.francoisbotha.bdgasadmin.services;

import io.francoisbotha.bdgasadmin.domain.dao.TeamRepository;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.domain.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

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


    public Team create(Team team) {
        return teamRepository.save(team);
    }

    public void delete(String id)  throws EntityNotFoundException {

        Team team = teamRepository.findOneById(id);

        teamRepository.delete(team);
    }

}



