package com.supernova.ai.Service.admin;

import com.supernova.ai.DTO.admin.AdminLoginDto;
import com.supernova.ai.DTO.admin.AdminSignUpDto;
import com.supernova.ai.DTO.user.UserDto;
import com.supernova.ai.Entity.*;
import com.supernova.ai.Repository.*;
import com.supernova.ai.Repository.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        usersEntity.setPassword(adminDto.getPassword());


        UsersEntity usersEntity1 = adminRepository.save(usersEntity);

        populateUserRoles(usersEntity);
        populateRolePermissions();

        return usersEntity1;
    }


    public UsersEntity adduser(UserDto usersDto){

        Optional<UsersEntity> existingUser = adminRepository.findByEmail(usersDto.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email already exists!");
        }

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setFirstName(usersDto.getFirstName());
        usersEntity.setLastName(usersDto.getLastName());
        usersEntity.setEmail(usersDto.getEmail());
        usersEntity.setCreatedAt(LocalDateTime.now());
        usersEntity.setUpdatedAt(LocalDateTime.now());
        usersEntity.setPassword(usersDto.getPassword());

        

         adminRepository.save(usersEntity);
        
        asignRoles(usersDto);

        return  usersEntity;
    }

    private void asignRoles(UserDto usersDto) {

        Optional<RolesEntity> rolesEntity = rolesRepository.findByRoleName(usersDto.getRole_name());
        Optional<UsersEntity> usersEntity = adminRepository.findByEmail(usersDto.getEmail());
        Optional<TeamsEntity> teamsEntity = teamsRepository.findByTeamName(usersDto.getTeam_name());
        if(rolesEntity.isPresent() && usersEntity.isPresent() && teamsEntity.isPresent()) {

            UserRolesEntity userRolesEntity = new UserRolesEntity();
            userRolesEntity.setUser(usersEntity.get());
            userRolesEntity.setTeam(teamsEntity.get());
            userRolesEntity.setRole(rolesEntity.get());

            userRolesRepository.save(userRolesEntity);

        }
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

    public HttpStatus adminLogin(AdminLoginDto adminLoginDto){

        if(adminRepository.findByEmail(adminLoginDto.getEmail()).isPresent()){

            if(adminRepository.findByEmail(adminLoginDto.getEmail()).get().getPassword().equals(adminLoginDto.getPassword())){
                return HttpStatus.OK;
            }
            else{
                return HttpStatus.UNAUTHORIZED;
            }
        }

        else{

            return HttpStatus.NOT_FOUND;
        }

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
