package com.supernova.ai.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Chats")
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(name = "temperature")
    private Float temperature;

    @Column(name = "context", columnDefinition = "TEXT")
    private String context;

    @Column(name = "chat_question", columnDefinition = "TEXT")
    private String chatQuestion;

    @Column(name = "chat_reply", columnDefinition = "TEXT")
    private String chatReply;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ChatEntity() {
    }

    public ChatEntity(Long chatId, UsersEntity user, ProjectEntity project, Float temperature, String context, String chatQuestion, String chatReply, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.chatId = chatId;
        this.user = user;
        this.project = project;
        this.temperature = temperature;
        this.context = context;
        this.chatQuestion = chatQuestion;
        this.chatReply = chatReply;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getChatQuestion() {
        return chatQuestion;
    }

    public void setChatQuestion(String chatQuestion) {
        this.chatQuestion = chatQuestion;
    }

    public String getChatReply() {
        return chatReply;
    }

    public void setChatReply(String chatReply) {
        this.chatReply = chatReply;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
