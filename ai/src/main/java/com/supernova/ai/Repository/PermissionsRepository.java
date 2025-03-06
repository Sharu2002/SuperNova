package com.supernova.ai.Repository;

import com.supernova.ai.Entity.PermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionsRepository extends JpaRepository<PermissionsEntity, Integer> {

    Optional<PermissionsEntity> findByPermissionName(String permissionName);
}
