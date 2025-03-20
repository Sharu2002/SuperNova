package com.supernova.ai.Repository;

import com.supernova.ai.Entity.DocumentEntity;
import com.supernova.ai.Entity.ProjectEntity;
import com.supernova.ai.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer> {
    DocumentEntity findByTitle(String documentName);

    List<DocumentEntity> findByUserAndProject(UsersEntity user, ProjectEntity project);
}
