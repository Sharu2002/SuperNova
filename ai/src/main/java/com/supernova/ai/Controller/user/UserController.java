package com.supernova.ai.Controller.user;

import com.supernova.ai.DTO.admin.AdminLoginDto;
import com.supernova.ai.DTO.chatDto.ChatDto;
import com.supernova.ai.DTO.chatDto.ChatMessageDto;
import com.supernova.ai.DTO.project.ProjectDto;
import com.supernova.ai.DTO.user.UserLoginDto;
import com.supernova.ai.Entity.ChatEntity;
import com.supernova.ai.Entity.ProjectEntity;
import com.supernova.ai.Repository.admin.AdminRepository;
import com.supernova.ai.Service.session.SessionService;
import com.supernova.ai.Service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;


    @PostMapping("/userLogin")
    public ResponseEntity<String> adminLogin(@RequestBody UserLoginDto userLoginDto) throws IOException {

        HttpStatus status = userService.userLogin(userLoginDto);

        if (status.isSameCodeAs(HttpStatus.OK)) {

            sessionService.setAttribute("userEmail", userLoginDto.getEmail());

            System.out.println("session anme login : " + sessionService.getAttribute("userEmail").toString() );

            return ResponseEntity.status(status).body("Successfully logged in"); // Returns 401

        } else if (status.isSameCodeAs(HttpStatus.UNAUTHORIZED)) {

            return ResponseEntity.status(status).body("InCorrect password!!!!"); // Returns 401
        }
        else
            return ResponseEntity.status(status).body("User Not Found!!!!"); // Returns 401


    }

    @GetMapping("/userName")
    public String getUserName() {

        return sessionService.getAttribute("userName").toString();
    }

    @PostMapping("/createProject")
    public ProjectEntity createProject(@RequestBody ProjectDto projectDto)  {

        return userService.createProject(projectDto);
    }

    @GetMapping("/getProjects")
    public List<String> getProjects() {

        return userService.getProjects();
    }

    @GetMapping("/getDocuments")
    public List<String> getDocuments(@RequestParam String projectName) {

        return userService.getDocuments(projectName);
    }

    //logout
    @GetMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {

        String username = (String) sessionService.getAttribute("userId");

//        if (username != null) {
//            // Save the files list to database before invalidating session
//            @SuppressWarnings("unchecked")
//            List<String> userFiles = (List<String>) sessionService.getAttribute("userFiles");
//            if (userFiles != null) {
//                UserFiles filesEntity = new UserFiles(username, userFiles);
//                userFileRepository.save(filesEntity);
//            }
//        }

        sessionService.invalidate();
        System.out.println("Session invalidated");
        response.sendRedirect("http://localhost:3000");
    }

//    @PostMapping("/newChat")
//    public ChatEntity newChat(@RequestBody ChatDto chatDto){
//
//        return userService.newChat(chatDto);
//    }


    @GetMapping("/chatCount")
    public List<Long> chatCount(@RequestParam String projectName) {


        return userService.chatCount(projectName);
    }

    @GetMapping("/chatHistory")
    public List<ChatMessageDto> chatHistory(@RequestParam String projectName, @RequestParam Long chatId) {


        return userService.chatHistory(projectName, chatId);
    }


}
