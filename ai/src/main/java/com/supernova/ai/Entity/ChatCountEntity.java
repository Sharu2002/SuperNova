package com.supernova.ai.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ChatCount")
public class ChatCountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    public ChatCountEntity() {
    }

    public ChatCountEntity(Long chatId, UsersEntity user, ProjectEntity project) {
        this.chatId = chatId;
        this.user = user;
        this.project = project;
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
}
