package com.supernova.ai.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "UserRoles")
@IdClass(UserRoleId.class)
@Data
public class UserRolesEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RolesEntity role;

    @Id
    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private TeamsEntity team;
}
