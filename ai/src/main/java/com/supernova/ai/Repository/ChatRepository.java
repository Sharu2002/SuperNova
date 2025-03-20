package com.supernova.ai.Repository;

import com.supernova.ai.Entity.ChatEntity;
import com.supernova.ai.Entity.ProjectEntity;
import com.supernova.ai.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {

    List<ChatEntity> findByChatIdAndUserAndProject(Long chatId, UsersEntity user, ProjectEntity project);

    List<ChatEntity> findByUserAndProject(UsersEntity user, ProjectEntity project);


}
