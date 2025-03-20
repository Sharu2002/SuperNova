package com.supernova.ai.Repository;

import com.supernova.ai.Entity.ProjectEntity;
import com.supernova.ai.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<ProjectEntity, Integer> {

    Optional<ProjectEntity> findByProjectTitle(String projectTitle);

    List<ProjectEntity> findByUser(UsersEntity user);
}
