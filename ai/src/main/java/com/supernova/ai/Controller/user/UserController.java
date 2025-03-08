package com.supernova.ai.Controller.user;

import com.supernova.ai.DTO.admin.AdminLoginDto;
import com.supernova.ai.DTO.user.UserLoginDto;
import com.supernova.ai.Repository.admin.AdminRepository;
import com.supernova.ai.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;


    @PostMapping("/userLogin")
    public ResponseEntity<String> adminLogin(@RequestBody UserLoginDto userLoginDto) throws IOException {

        HttpStatus status = userService.adminLogin(userLoginDto);

        if (status.isSameCodeAs(HttpStatus.OK)) {
            return ResponseEntity.status(status).body("Successfully logged in"); // Returns 401
        } else if (status.isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
            return ResponseEntity.status(status).body("InCorrect password!!!!"); // Returns 401
        }
        else
            return ResponseEntity.status(status).body("User Not Found!!!!"); // Returns 401


    }

}
