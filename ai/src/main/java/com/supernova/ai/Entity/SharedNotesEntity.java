package com.supernova.ai.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class SharedNotesEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long sharedNotesId;

    @Column(name = "senderId")
    private Long senderId;

    @Column(name="projectName")
    private String projectName;

    @Column(name = "receiverId")
    private Long receiverId;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "date" , columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    public SharedNotesEntity() {
    }

    public SharedNotesEntity(Long sharedNotesId, Long senderId, String projectName, Long receiverId, String notes, LocalDateTime date) {
        this.sharedNotesId = sharedNotesId;
        this.senderId = senderId;
        this.projectName = projectName;
        this.receiverId = receiverId;
        this.notes = notes;
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public Long getSharedNotesId() {
        return sharedNotesId;
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
