package com.supernova.ai.Service.admin;

import com.supernova.ai.DTO.admin.AdminSignUpDto;
import com.supernova.ai.Entity.*;
import com.supernova.ai.Repository.*;
import com.supernova.ai.Repository.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    TeamsRepository teamsRepository;

    @Autowired
    PermissionsRepository permissionsRepository;

    @Autowired
    UserRolesRepository userRolesRepository;

    @Autowired
    RolesPermissionRepository rolesPermissionRepository;

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

//    public Users addUsers(Users user){
//
//        return usersRepository.save(user);
//    }

    public UsersEntity adminSignup(AdminSignUpDto adminDto){

        Optional<UsersEntity> existingUser = adminRepository.findByEmail(adminDto.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Admin with this email already exists!");
        }

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setFirstName(adminDto.getFirstName());
        usersEntity.setLastName(adminDto.getLastName());
        usersEntity.setEmail(adminDto.getEmail());
        usersEntity.setCreatedAt(LocalDateTime.now());
        usersEntity.setUpdatedAt(LocalDateTime.now());


        UsersEntity usersEntity1 = adminRepository.save(usersEntity);

        populateUserRoles(usersEntity);
        populateRolePermissions();

        return usersEntity1;
    }

    private void populateRolePermissions() {

        RolePermissionsEntity rolePermissionsEntity = new RolePermissionsEntity();

        Optional<RolesEntity> rolesEntity = rolesRepository.findByRoleName("admin");

        if(rolesEntity.isPresent()) {

            Optional<PermissionsEntity> permissionsEntity = permissionsRepository.findByPermissionName("read-permission");

            if(permissionsEntity.isPresent()) {
                rolePermissionsEntity.setPermission(permissionsEntity.get());
                rolePermissionsEntity.setRole(rolesEntity.get());
            }

            Optional<PermissionsEntity> permissionsEntity1 = permissionsRepository.findByPermissionName("write-permission");

            if(permissionsEntity.isPresent()) {
                rolePermissionsEntity.setPermission(permissionsEntity.get());
                rolePermissionsEntity.setRole(rolesEntity.get());
            }

            Optional<PermissionsEntity> permissionsEntity2 = permissionsRepository.findByPermissionName("edit-permission");

            if(permissionsEntity.isPresent()) {
                rolePermissionsEntity.setPermission(permissionsEntity.get());
                rolePermissionsEntity.setRole(rolesEntity.get());
            }

            Optional<PermissionsEntity> permissionsEntity3 = permissionsRepository.findByPermissionName("download-permission");

            if(permissionsEntity.isPresent()) {
                rolePermissionsEntity.setPermission(permissionsEntity.get());
                rolePermissionsEntity.setRole(rolesEntity.get());
            }


        }

        rolesPermissionRepository.save(rolePermissionsEntity);

    }

    public void adminLogin(){

    }


    public void populateUserRoles(UsersEntity usersEntity){
        UserRolesEntity userRoles = new UserRolesEntity();

        Optional<RolesEntity> rolesEntity = rolesRepository.findByRoleName("admin");


        Optional<UsersEntity> usersEntity1 = adminRepository.findByEmail(usersEntity.getEmail());

        Optional<TeamsEntity> teamsEntity = teamsRepository.findByTeamName("Global-Team");


        if(rolesEntity.isPresent() && usersEntity1.isPresent() && teamsEntity.isPresent()) {
            userRoles.setRole(rolesEntity.get());
            System.out.println("role : " + rolesEntity.get());
            userRoles.setUser(usersEntity1.get());
            userRoles.setTeam(teamsEntity.get());
        }

        userRolesRepository.save(userRoles);


    }
}
