package com.supernova.ai.Repository.admin;

import com.supernova.ai.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<UsersEntity,Integer> {

    Optional<UsersEntity> findByEmail(String email);

}

