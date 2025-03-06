package com.supernova.ai.Repository;

import com.supernova.ai.Entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {

    Optional<RolesEntity> findByRoleName(String roleName);
}
