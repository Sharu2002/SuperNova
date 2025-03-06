package com.supernova.ai.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Teams")
@Data
public class TeamsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name", unique = true, length = 255)
    private String teamName;

    @OneToMany(mappedBy = "team")
    private Set<UserRolesEntity> userRoles = new HashSet<>();

    public TeamsEntity() {
    }

    public TeamsEntity(Long id, String teamName, Set<UserRolesEntity> userRoles) {
        this.id = id;
        this.teamName = teamName;
        this.userRoles = userRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Set<UserRolesEntity> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRolesEntity> userRoles) {
        this.userRoles = userRoles;
    }
}