package com.supernova.ai.DTO.project;

import com.supernova.ai.Entity.UsersEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class ProjectDto {


    private String projectTitle;

    public ProjectDto() {
    }

    public ProjectDto(Long id, String projectTitle) {
        this.projectTitle = projectTitle;
    }



    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

}
