package com.supernova.ai.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class SharedNotesEntityDto {

    private Long sharedNotesId;

    private Long senderId;

    private String senderName;

    private String projectName;

    private Long receiverId;

    private String notes;


    private LocalDateTime date;

    public SharedNotesEntityDto() {
    }

    public SharedNotesEntityDto(Long sharedNotesId, Long senderId, String senderName, String projectName, Long receiverId, String notes, LocalDateTime date) {
        this.sharedNotesId = sharedNotesId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.projectName = projectName;
        this.receiverId = receiverId;
        this.notes = notes;
        this.date = date;
    }

    public Long getSharedNotesId() {
        return sharedNotesId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setSharedNotesId(Long sharedNotesId) {
        this.sharedNotesId = sharedNotesId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}