package com.supernova.ai.Controller;

import com.supernova.ai.tools.AiEmailer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



@RestController
class MyController {

    private final ChatClient chatClient;

    private final ChatClient toolsChatClient;


    @Autowired
    AiEmailer aiEmailer;


    private final InMemoryChatMemoryRepository memoryRepository = new InMemoryChatMemoryRepository();

    private final Map<String, PromptChatMemoryAdvisor> chatMemoryAdvisors = new ConcurrentHashMap<>();


    ChatMemory chatMemory = MessageWindowChatMemory.builder()

            .chatMemoryRepository(memoryRepository)
            .maxMessages(10)
            .build();



    public MyController(ChatClient.Builder chatClientBuilder, QuestionAnswerAdvisor questionAnswerAdvisor, AiEmailer aiEmailer) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(questionAnswerAdvisor)
                .defaultTools(aiEmailer)
                .build();

        this.toolsChatClient = chatClientBuilder
                .defaultAdvisors(questionAnswerAdvisor)
                .build();

    }

    @GetMapping("/{user}/inquire")
    String generation(@PathVariable String user, @RequestParam String userInput) {

        var advisor = this.chatMemoryAdvisors.computeIfAbsent(user, k ->  PromptChatMemoryAdvisor.builder(chatMemory).build());


//        var systemPromptTemplate = """
//                            You are an AI assistant that help people with easier knowledge retrieval and to send emails.
//                            Information will be presented below. If there is no information, then return a polite response saying, Sorry I dont have the context to answer this question
//                        """;

        var systemPromptTemplate = """
You are an AI assistant that helps people with knowledge retrieval and sending emails using tools.
Only call the `send_email` tool if the user asks you to send an email.
Do not call tools for greetings or other information retrieval tasks.
""";

        if (userInput.toLowerCase().contains("email")) {
            return this.chatClient.prompt()
                    .user(userInput)
                    .system(systemPromptTemplate)
                    .call()
                    .content();
        }
        else {
            var prompt = """
                            You are an AI assistant that help people with easier knowledge retrieval..
                           Information will be presented below. If there is no information, then return a polite response saying, Sorry I dont have the context to answer this question
                      """;

            return toolsChatClient.prompt()
                    .user(userInput)
                    .system(prompt)
                    .call()
                    .content();

        }
    }
}