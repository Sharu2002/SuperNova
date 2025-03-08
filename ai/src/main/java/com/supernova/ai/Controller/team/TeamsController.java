package com.supernova.ai.Controller.team;

import com.supernova.ai.Service.roles.RoleService;
import com.supernova.ai.Service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamsController {

    @Autowired
    TeamService teamService;

    @GetMapping("/getTeams")
    public List<String> getTeams(){

        return teamService.getTeams();
    }
}
