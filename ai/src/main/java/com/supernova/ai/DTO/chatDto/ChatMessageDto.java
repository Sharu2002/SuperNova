package com.supernova.ai.DTO.chatDto;

public class ChatMessageDto {
    private String chatQuestion;
    private String chatReply;


    public ChatMessageDto() {
    }
    public ChatMessageDto(String chatQuestion, String chatReply) {
        this.chatQuestion = chatQuestion;
        this.chatReply = chatReply;
    }

    //setter
    public void setQuestion(String chatQuestion) {
        this.chatQuestion = chatQuestion;
    }

    public void setReply(String chatReply) {
        this.chatReply = chatReply;
    }

    public String getQuestion() {
        return chatQuestion;
    }

    public String getReply() {
        return chatReply;
    }
}
