package com.supernova.ai.Repository;

import com.supernova.ai.Entity.ChatCountEntity;
import com.supernova.ai.Entity.ChatEntity;
import com.supernova.ai.Entity.ProjectEntity;
import com.supernova.ai.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatCountRepository extends JpaRepository<ChatCountEntity, Integer> {

    List<ChatCountEntity> findByUserAndProject(UsersEntity user, ProjectEntity project);

}
