package com.supernova.ai.Controller.roles;

import com.supernova.ai.Entity.RolesEntity;
import com.supernova.ai.Repository.RolesRepository;
import com.supernova.ai.Service.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RolesController {

    @Autowired
    RoleService roleService;

    @GetMapping("/getRoles")
    public List<String> getRoles(){

        return roleService.getRoles();
    }
}
