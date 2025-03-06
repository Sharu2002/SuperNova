package com.supernova.ai.Repository;

import com.supernova.ai.Entity.RolePermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesPermissionRepository extends JpaRepository<RolePermissionsEntity, Integer> {
}
