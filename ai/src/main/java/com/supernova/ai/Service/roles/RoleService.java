package com.supernova.ai.Service.roles;

import com.supernova.ai.DTO.user.UserLoginDto;
import com.supernova.ai.Entity.RolesEntity;
import com.supernova.ai.Repository.RolesRepository;
import com.supernova.ai.Repository.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService {

    @Autowired
    RolesRepository rolesRepository;

    public List<String> getRoles( ){

        List<String> roles = new ArrayList<>();


        for(RolesEntity role : rolesRepository.findAll()){

            roles.add(role.getRoleName());
        }
        return roles;

    }
}
