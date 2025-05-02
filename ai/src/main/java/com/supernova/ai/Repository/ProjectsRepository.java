package com.supernova.ai.Repository;

import com.supernova.ai.Entity.ProjectEntity;
import com.supernova.ai.Entity.UsersEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<ProjectEntity, Integer> {

    Optional<ProjectEntity> findByProjectTitle(String projectTitle);

    Optional<ProjectEntity> findByProjectTitleAndUser(String projectTitle, UsersEntity user);

    List<ProjectEntity> findByUser(UsersEntity user);

    @Modifying
    @Transactional
    @Query("UPDATE ProjectEntity p SET p.notes = :notes WHERE p.projectTitle = :projectName AND p.user.id = :userId")
    void updateNotes(@Param("userId") Long userId, @Param("projectName") String projectName, @Param("notes") String notes);
}
