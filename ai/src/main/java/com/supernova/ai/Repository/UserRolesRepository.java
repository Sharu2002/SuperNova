package com.supernova.ai.Repository;

import com.supernova.ai.Entity.UserRolesEntity;
import com.supernova.ai.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRolesEntity, Integer> {

    Optional<UserRolesEntity> findByUser(UsersEntity usersEntity);
}
