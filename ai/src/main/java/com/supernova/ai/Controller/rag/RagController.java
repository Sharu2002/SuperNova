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

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam  String emailId,@RequestParam String subject,@RequestParam String body) {

        // Implement your email sending logic here
        // You can use JavaMailSender or any other library to send emails

        String message =  "Send an email to  " + emailId + " with subject: " + subject + " and body: " + body;

        return callLlamaAgent(message);
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

        if (isDirectEmailInstruction(query)) {

            System.out.println("\n\n\n\n\ninside\n\n\n\n\n");
            // ⚡ Case 2: Send email directly using Flask LLaMA agent
            response = callLlamaAgent(query);
            ragService.saveHistory(query, projectName, chatId, response);  // Save anyway
            return ResponseEntity.ok("Email triggered: " + response);
        }

        // 🧠 Case 1: RAG followed by optional email
        Prompt prompt = ragService.getResponse(query);
        response = chatClient.call(prompt).getResult().getOutput().getContent();

        ragService.saveHistory(query, projectName, chatId, response);

        String emailResponse =response;
        String emailId ="";
        if (query.toLowerCase().contains("email")) {
            // ✉️ Ask Flask agent to send the RAG answer

            for(String i : query.split(" ")){
                if(i.contains("@")){
                    emailId = i;
                }
            }
            String emailInstruction = "Send email to "+ emailId + " with body as'" + response + "' and and suitable subject";
            System.out.println("\n\n\n\n\n\n emailInstruction : " + emailInstruction + "\n\n\n\n\n");
            emailResponse = callLlamaAgent(emailInstruction);
            System.out.println(" Email result: " + emailResponse);
        }

        return ResponseEntity.ok(response + " \n\n\n" + emailResponse);
    }

    public boolean isDirectEmailInstruction(String query) {
        String lower = query.toLowerCase();
        return lower.startsWith("send an email to");
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
