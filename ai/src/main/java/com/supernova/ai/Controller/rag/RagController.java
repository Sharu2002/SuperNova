package com.supernova.ai.Controller.rag;

import com.supernova.ai.Service.rag.RagService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RagController {

    @Autowired
    RagService ragService;

    private final ChatClient chatClient;

    public RagController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping("/rag")
    public ResponseEntity<String> generateAnswer(@RequestParam String query, @RequestParam Long chatId, @RequestParam String projectName) {

        Prompt prompt =  ragService.getResponse(query);

        String response = chatClient.call(prompt).getResult().getOutput().getContent();


        ragService.saveHistory(query, projectName , chatId , response);

        return ResponseEntity.ok(chatClient.call(prompt).getResult().getOutput().getContent());
    }
}
