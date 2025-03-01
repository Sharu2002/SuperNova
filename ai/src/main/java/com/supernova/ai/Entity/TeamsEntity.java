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
}