package com.supernova.ai.Repository;

import com.supernova.ai.Entity.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRolesEntity, Integer> {
}
