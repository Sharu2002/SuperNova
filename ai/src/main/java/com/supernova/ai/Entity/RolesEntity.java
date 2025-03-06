package com.supernova.ai.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Roles")
@Data
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name", unique = true, length = 255)
    private String roleName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "role")
    private Set<RolePermissionsEntity> rolePermissions = new HashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<UserRolesEntity> userRoles = new HashSet<>();

    public RolesEntity() {
    }

    public RolesEntity(Long id, String roleName, String description, Set<RolePermissionsEntity> rolePermissions, Set<UserRolesEntity> userRoles) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.rolePermissions = rolePermissions;
        this.userRoles = userRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<RolePermissionsEntity> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(Set<RolePermissionsEntity> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public Set<UserRolesEntity> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRolesEntity> userRoles) {
        this.userRoles = userRoles;
    }
}
