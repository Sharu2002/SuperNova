package com.supernova.ai.Service.rag;

import com.supernova.ai.DTO.chatDto.ChatMessageDto;
import com.supernova.ai.Entity.ChatCountEntity;
import com.supernova.ai.Entity.ChatEntity;
import com.supernova.ai.Entity.ProjectEntity;
import com.supernova.ai.Entity.UsersEntity;
import com.supernova.ai.Repository.ChatCountRepository;
import com.supernova.ai.Repository.ChatRepository;
import com.supernova.ai.Repository.ProjectsRepository;
import com.supernova.ai.Repository.admin.AdminRepository;
import com.supernova.ai.Service.session.SessionService;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final VectorStore vectorStore;

    @Autowired
    SessionService sessionService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ProjectsRepository projectRepository;

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatCountRepository chatCountRepository;

    public RagService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    private String prompt = """
            Your task is to answer the questions about Indian Constitution. Use the information from the DOCUMENTS
            section to provide accurate answers. If unsure or if the answer isn't found in the DOCUMENTS section, 
            simply state that you don't know the answer.
                        
            QUESTION:
            {input}
                        
            DOCUMENTS:
            {documents}
                        
            """;


    public Prompt getResponse(String query) {

        List<Document> similarDocuments = vectorStore.similaritySearch(query);
        String information = similarDocuments.stream()
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        var systemPromptTemplate = new SystemPromptTemplate(
                """
                            Use only the following information to answer the question.
                            Do not use any other information. If you do not know, simply answer: Unknown.
                            {information}
                        """);
        var systemMessage = systemPromptTemplate.createMessage(Map.of("information", information));
        var userPromptTemplate = new PromptTemplate("{query}");
        var userMessage = userPromptTemplate.createMessage(Map.of("query", query));

        var prompt = new Prompt(List.of(systemMessage, userMessage));

        return prompt;
    }


    public void saveHistory(String query, String projectName, Long chatId, String response) {



        ProjectEntity projectEntity = projectRepository.findByProjectTitle(projectName).get();


        String email = sessionService.getAttribute("userEmail").toString();

        UsersEntity usersEntity = adminRepository.findByEmail(email).get();

        ChatCountEntity chatCountEntity = new ChatCountEntity();

        System.out.println("\n\n\nChat id : " + chatId);
        chatCountEntity.setChatId(chatId);
        chatCountEntity.setProject(projectEntity);
        chatCountEntity.setUser(usersEntity);

        chatCountRepository.save(chatCountEntity);

        ChatEntity chatEntity = new ChatEntity();

        chatEntity.setChatId(chatId);
        chatEntity.setChatQuestion(query);
        chatEntity.setChatReply(response);
        chatEntity.setUser(usersEntity);
        chatEntity.setProject(projectEntity);

        chatRepository.save(chatEntity);
    }
}
