package com.supernova.ai.Service.user;

import com.supernova.ai.DTO.admin.AdminLoginDto;
import com.supernova.ai.DTO.chatDto.ChatDto;
import com.supernova.ai.DTO.chatDto.ChatMessageDto;
import com.supernova.ai.DTO.project.ProjectDto;
import com.supernova.ai.DTO.user.UserLoginDto;
import com.supernova.ai.Entity.*;
import com.supernova.ai.Repository.ChatCountRepository;
import com.supernova.ai.Repository.ChatRepository;
import com.supernova.ai.Repository.DocumentRepository;
import com.supernova.ai.Repository.ProjectsRepository;
import com.supernova.ai.Repository.admin.AdminRepository;
import com.supernova.ai.Service.session.SessionService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ProjectsRepository projectRepository;

    @Autowired
    SessionService sessionService;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatCountRepository chatCountRepository;


    public HttpStatus userLogin(UserLoginDto userLoginDto) {

        if (adminRepository.findByEmail(userLoginDto.getEmail()).isPresent()) {

            if (adminRepository.findByEmail(userLoginDto.getEmail()).get().getPassword().equals(userLoginDto.getPassword())) {
                sessionService.setAttribute("userName", adminRepository.findByEmail(userLoginDto.getEmail()).get().getFirstName());

                return HttpStatus.OK;
            } else {
                return HttpStatus.UNAUTHORIZED;
            }
        } else {

            return HttpStatus.NOT_FOUND;
        }

    }

    //create new project
    public ProjectEntity createProject(ProjectDto projectDto) {

        Optional<ProjectEntity> projectEntity = projectRepository.findByProjectTitle(projectDto.getProjectTitle());
        if (projectEntity.isPresent()) {
            throw new RuntimeException("Project Title already exists!");
        }


        String userEmail = sessionService.getAttribute("userEmail").toString();

        Optional<UsersEntity> user = adminRepository.findByEmail(userEmail);

        ProjectEntity projectEntity1 = new ProjectEntity();

        if (user.isPresent()) {

            projectEntity1.setProjectTitle(projectDto.getProjectTitle());
            projectEntity1.setCreatedAt(LocalDateTime.now());
            projectEntity1.setUpdatedAt(LocalDateTime.now());
            projectEntity1.setUser(user.get());
        }
//

        return projectRepository.save(projectEntity1);
    }

    //get Projects
    public List<String> getProjects() {

        String email = sessionService.getAttribute("userEmail").toString();

        UsersEntity usersEntity = adminRepository.findByEmail(email).get();

        List<ProjectEntity> projects = projectRepository.findByUser(usersEntity);
        List<String> projectTitles = new ArrayList<>();

        for (ProjectEntity project : projects) {
            projectTitles.add(project.getProjectTitle());
        }

        return projectTitles;
    }

    public List<String> getDocuments(String projectName) {


        String email = sessionService.getAttribute("userEmail").toString();

        UsersEntity usersEntity = adminRepository.findByEmail(email).get();

        System.out.println("\n\nHellooooooooo");
        ProjectEntity projectEntity = projectRepository.findByProjectTitle(projectName).get();

        System.out.println("\n\nUser id : " + usersEntity.getId());

        System.out.println("\n\nProject id : " + projectEntity.getId());

        List<DocumentEntity> documentEntities = documentRepository.findByUserAndProject(usersEntity, projectEntity);

        List<String> documentTitles = new ArrayList<>();

        for (DocumentEntity doc : documentEntities) {
            documentTitles.add(doc.getTitle());
        }

        return documentTitles;
    }

    public List<ChatMessageDto> chatHistory(String projectName, Long chatId) {


        String email = sessionService.getAttribute("userEmail").toString();

        UsersEntity usersEntity = adminRepository.findByEmail(email).get();

        ProjectEntity projectEntity = projectRepository.findByProjectTitle(projectName).get();

        List<ChatEntity> chatEntities = chatRepository.findByChatIdAndUserAndProject(chatId, usersEntity, projectEntity);

        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
        for(ChatEntity chat : chatEntities)
        {
            ChatMessageDto chatMessageDto = new ChatMessageDto();

            chatMessageDto.setQuestion(chat.getChatQuestion());
            chatMessageDto.setReply(chat.getChatReply());

            chatMessageDtos.add(chatMessageDto);

        }

        return  chatMessageDtos;

    }

    public List<Long> chatCount(String projectName) {


        String email = sessionService.getAttribute("userEmail").toString();

        UsersEntity usersEntity = adminRepository.findByEmail(email).get();


        ProjectEntity projectEntity = projectRepository.findByProjectTitle(projectName).get();

//        List<ChatCountEntity> chatCountEntityList = chatCountRepository.findByUserAndProject( usersEntity, projectEntity);
//
//        HashSet<Long> chats = new HashSet<>();
//
//        for(ChatCountEntity chatCountEntity : chatCountEntityList){
//
//            chats.add(chatCountEntity.getChatId());
//        }

        List<ChatEntity> chatEntities = chatRepository.findByUserAndProject(usersEntity, projectEntity);

        HashSet<Long> chats =  new HashSet<>();
        for(ChatEntity chat : chatEntities) {

            System.out.println("\n\n\n\nChat count : " + chat.getChatId());

            chats.add(chat.getChatId());
        }

        return chats.stream().toList();
    }


//    //create new chat under a project
//    public ChatEntity newChat(ChatDto chatDto){
//
//        Optional<ChatEntity> chatEntity = chatRepository.findByChatTitle(chatDto.getChatTitle());
//        if (chatEntity.isPresent()) {
//            throw new RuntimeException("Chat Title already exists!");
//        }
//
//        sessionService.setAttribute("userEmail", "sharu2903@gmail.com");
//
//        String userEmail = sessionService.getAttribute("userEmail").toString();
//
//        Optional<UsersEntity> user = adminRepository.findByEmail(userEmail);
//
//
//}
}