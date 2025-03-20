package com.supernova.ai.Controller.admin;

import com.supernova.ai.DTO.admin.AdminLoginDto;
import com.supernova.ai.DTO.admin.AdminSignUpDto;
import com.supernova.ai.DTO.user.ListUsersDto;
import com.supernova.ai.DTO.user.UserDto;
import com.supernova.ai.Entity.UsersEntity;
import com.supernova.ai.Repository.admin.AdminRepository;
import com.supernova.ai.Service.admin.AdminService;
import com.supernova.ai.Service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminService adminService;

    @Autowired
    SessionService sessionService;


    @PostMapping("/adminSignup")
    public UsersEntity adminSignup(@RequestBody AdminSignUpDto adminSignUpDto) {

        return adminService.adminSignup(adminSignUpDto);
    }

    @PostMapping("/adminLogin")
    public ResponseEntity<String> adminLogin(@RequestBody AdminLoginDto adminLoginDto) throws IOException {

        HttpStatus status = adminService.adminLogin(adminLoginDto);

        if (status.isSameCodeAs(HttpStatus.OK)) {
            sessionService.setAttribute("userEmail", adminLoginDto.getEmail());
            return ResponseEntity.status(status).body("Successfully logged in"); // Returns 401
        } else if (status.isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
            return ResponseEntity.status(status).body("InCorrect password!!!!"); // Returns 401
        }
        else
            return ResponseEntity.status(status).body("User Not Found!!!!"); // Returns 401
    }

    @PostMapping("/addUser")
    public UsersEntity addUser(@RequestBody UserDto usersDto){

        return adminService.adduser(usersDto);
    }

    @GetMapping("/getAllUsers")
    public List<ListUsersDto> getAllUsers(){
        return adminService.getAllUsers();
    }




}
