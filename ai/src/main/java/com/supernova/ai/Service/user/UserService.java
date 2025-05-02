package com.supernova.ai.Service.user;

import com.supernova.ai.DTO.SharedNotesEntityDto;
import com.supernova.ai.DTO.admin.AdminLoginDto;
import com.supernova.ai.DTO.chatDto.ChatDto;
import com.supernova.ai.DTO.chatDto.ChatMessageDto;
import com.supernova.ai.DTO.project.ProjectDto;
import com.supernova.ai.DTO.user.UserLoginDto;
import com.supernova.ai.Entity.*;
import com.supernova.ai.Repository.*;
import com.supernova.ai.Repository.admin.AdminRepository;
import com.supernova.ai.Service.session.SessionService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
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

    @Autowired
    SharedNotesRepository sharedNotesRepository;


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

    public void updateNotes(String projectName, String notes) {

        String email = sessionService.getAttribute("userEmail").toString();

        UsersEntity usersEntity = adminRepository.findByEmail(email).get();

        Long userId = usersEntity.getId();

         projectRepository.updateNotes(userId, projectName, notes);
    }

    public String getNotes(String projectName) {

        String email = sessionService.getAttribute("userEmail").toString();

        UsersEntity usersEntity = adminRepository.findByEmail(email).get();


        return projectRepository.findByProjectTitleAndUser(projectName,usersEntity).get().getNotes();
    }

    public SharedNotesEntity shareNotes(String projectName, String receiverEmail) {

        String email = sessionService.getAttribute("userEmail").toString();

        UsersEntity sender = adminRepository.findByEmail(email).get();

        UsersEntity receiver = adminRepository.findByEmail(receiverEmail).get();

        ProjectEntity projectEntity = projectRepository.findByProjectTitleAndUser(projectName, sender).get();

        SharedNotesEntity sharedNotesEntity = new SharedNotesEntity();

        sharedNotesEntity.setSenderId(sender.getId());
        sharedNotesEntity.setProjectName(projectName);
        sharedNotesEntity.setReceiverId(receiver.getId());
        sharedNotesEntity.setNotes(projectEntity.getNotes());
        sharedNotesEntity.setDate(LocalDateTime.now());


        sharedNotesRepository.save(sharedNotesEntity);

        return sharedNotesEntity;


    }

    public List<SharedNotesEntityDto> getSharedNotes() {

                String email = sessionService.getAttribute("userEmail").toString();

        UsersEntity user = adminRepository.findByEmail(email).get();

        List<SharedNotesEntity> sharedNotesEntity = sharedNotesRepository.findByReceiverId(user.getId());

        List<SharedNotesEntityDto> sharedNotesEntityDtos = new ArrayList<>();

        for(SharedNotesEntity sharedNotes : sharedNotesEntity){

            SharedNotesEntityDto sharedNotesEntityDto = new SharedNotesEntityDto();

            sharedNotesEntityDto.setProjectName(sharedNotes.getProjectName());
            sharedNotesEntityDto.setSharedNotesId(sharedNotes.getSharedNotesId());
            sharedNotesEntityDto.setSenderId(sharedNotes.getSenderId());
            sharedNotesEntityDto.setSenderName(adminRepository.findById(Math.toIntExact(sharedNotes.getSenderId())).get().getFirstName());
            sharedNotesEntityDto.setNotes(sharedNotes.getNotes());
            sharedNotesEntityDto.setReceiverId(sharedNotes.getReceiverId());
            sharedNotesEntityDto.setDate(sharedNotes.getDate());

            sharedNotesEntityDtos.add(sharedNotesEntityDto);


        }

        return sharedNotesEntityDtos;
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