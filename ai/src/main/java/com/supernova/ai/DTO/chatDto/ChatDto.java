package com.supernova.ai.DTO.chatDto;

public class ChatDto {

    private String projectTitle;

    private String chatQuestion;

    public ChatDto() {
    }

    public ChatDto(String projectTitle, String chatQuestion) {
        this.projectTitle = projectTitle;
        this.chatQuestion = chatQuestion;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getChatQuestion() {
        return chatQuestion;
    }

    public void setChatQuestion(String chatQuestion) {
        this.chatQuestion = chatQuestion;
    }
}
