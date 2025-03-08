package com.supernova.ai.Service.team;

import com.supernova.ai.Entity.RolesEntity;
import com.supernova.ai.Entity.TeamsEntity;
import com.supernova.ai.Repository.TeamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    TeamsRepository teamsRepository;

    public List<String> getTeams(){

        List<String> teams = new ArrayList<>();


        for(TeamsEntity team : teamsRepository.findAll()){

            teams.add(team.getTeamName());
        }
        return teams;

    }
}
