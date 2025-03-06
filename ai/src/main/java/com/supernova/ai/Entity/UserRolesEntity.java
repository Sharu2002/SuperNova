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

    public UserRolesEntity() {
    }

    public UserRolesEntity(UsersEntity user, RolesEntity role, TeamsEntity team) {
        this.user = user;
        this.role = role;
        this.team = team;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public RolesEntity getRole() {
        return role;
    }

    public void setRole(RolesEntity role) {
        this.role = role;
    }

    public TeamsEntity getTeam() {
        return team;
    }

    public void setTeam(TeamsEntity team) {
        this.team = team;
    }
}
