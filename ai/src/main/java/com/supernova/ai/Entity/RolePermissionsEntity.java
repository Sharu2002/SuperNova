package com.supernova.ai.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RolePermissions")
@IdClass(RolePermissionId.class)
@Data
public class RolePermissionsEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RolesEntity role;

    @Id
    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "permission_id")
    private PermissionsEntity permission;

    public RolePermissionsEntity() {
    }

    public RolePermissionsEntity(RolesEntity role, PermissionsEntity permission) {
        this.role = role;
        this.permission = permission;
    }

    public RolesEntity getRole() {
        return role;
    }

    public void setRole(RolesEntity role) {
        this.role = role;
    }

    public PermissionsEntity getPermission() {
        return permission;
    }

    public void setPermission(PermissionsEntity permission) {
        this.permission = permission;
    }
}