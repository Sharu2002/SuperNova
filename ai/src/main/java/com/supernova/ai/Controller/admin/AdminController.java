package com.supernova.ai.Controller.admin;

import com.supernova.ai.DTO.admin.AdminSignUpDto;
import com.supernova.ai.Entity.UsersEntity;
import com.supernova.ai.Repository.admin.AdminRepository;
import com.supernova.ai.Service.admin.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminService adminService;

//    @PostMapping("/addUser")
//    public Users addUser(@RequestBody Users user, HttpSession session){
//
//        session.setAttribute("userId", user.getUserName());
//        UserFiles userFiles = userFileRepository.findByUsername(user.getUserName())
//                .orElse(new UserFiles(user.getUserName(), new ArrayList<>()));
//
//        session.setAttribute("userFiles", userFiles.getFileNames());
//
//
//        return userService.addUsers(user);
//    }

    @PostMapping("/adminSignup")
    public UsersEntity adminSignup(@RequestBody AdminSignUpDto adminSignUpDto){

        return adminService.adminSignup(adminSignUpDto);
    }

    @GetMapping("/adminLogin")
    public void adminLogin(){

    }

}
