package com.supernova.ai.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Permissions")
@Data
public class PermissionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long id;

    @Column(name = "permission_name", unique = true, length = 255)
    private String permissionName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "permission")
    private Set<RolePermissionsEntity> rolePermissions = new HashSet<>();

    public PermissionsEntity() {
    }

    public PermissionsEntity(Long id, String permissionName, String description, Set<RolePermissionsEntity> rolePermissions) {
        this.id = id;
        this.permissionName = permissionName;
        this.description = description;
        this.rolePermissions = rolePermissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
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
}
