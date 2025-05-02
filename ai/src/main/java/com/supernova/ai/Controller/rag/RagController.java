package com.supernova.ai.Controller.rag;

import com.supernova.ai.Service.rag.RagService;
import org.json.JSONObject;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
//
//        Prompt prompt =  ragService.getResponse(query);
//
//
//
//        String response = chatClient.call(prompt).getResult().getOutput().getContent();
//
//
//        ragService.saveHistory(query, projectName , chatId , response);
//
//        return ResponseEntity.ok(chatClient.call(prompt).getResult().getOutput().getContent());

        String response;

        if (query.toLowerCase().contains("email")) {
            // 🔁 Forward to Flask LLaMA agent
            response = callLlamaAgent(query);
        } else {
            // 🧠 Standard RAG processing
            Prompt prompt = ragService.getResponse(query);
            response = chatClient.call(prompt).getResult().getOutput().getContent();
        }

        // 💾 Save conversation history regardless of path
        ragService.saveHistory(query, projectName, chatId, response);

        return ResponseEntity.ok(response);
    }

    public String callLlamaAgent(String userMessage) {
        String apiUrl = "http://localhost:5000/llama-agent"; // Flask app endpoint
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("message", userMessage);

        HttpEntity<String> request = new HttpEntity<>(jsonBody.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "❌ Failed to get response from LLaMA email agent: " + e.getMessage();
        }
    }


//    @PostMapping("/rag")
//    public ResponseEntity<String> generateAnswer(@RequestParam String query, @RequestParam Long chatId, @RequestParam String projectName) {
//
//        System.out.println("\n\nHeloooooooo nfjshfksdfjskdjfksjfjk");
//        Prompt prompt =  ragService.getResponse(query);
//
//        String response = chatClient.call(prompt).getResult().getOutput().getContent();
//
//
//        ragService.saveHistory(query, projectName , chatId , response);
//
//        return ResponseEntity.ok(chatClient.call(prompt).getResult().getOutput().getContent());
//    }

    @GetMapping("/rag-arabic")
    public ResponseEntity<String> generateAnswer1(@RequestParam String query, @RequestParam Long chatId, @RequestParam String projectName) {

        Prompt prompt =  ragService.getArabicResponse(query);

        String response = chatClient.call(prompt).getResult().getOutput().getContent();


        ragService.saveHistory(query, projectName , chatId , response);

        return ResponseEntity.ok(chatClient.call(prompt).getResult().getOutput().getContent());
    }

//    @GetMapping("/rag")
//    public ResponseEntity<String> generateAnswer(@RequestParam String query, @RequestParam Long chatId, @RequestParam String projectName) {
//
//        Prompt prompt =  ragService.BrdRiskAnalyser(query);
//
//
//        System.out.println("\n\n\n---------------promptdfed-------------\n\n\n");
//        System.out.println(prompt);
//
//        String response = chatClient.call(prompt).getResult().getOutput().getContent();
//
//
//        System.out.println("\n\n\n---------------RESPONSE-------------\n\n\n");
//        System.out.println(response);
//
//        ragService.saveHistory(query, projectName , chatId , response);
//
//        return ResponseEntity.ok(response);
//    }
}
