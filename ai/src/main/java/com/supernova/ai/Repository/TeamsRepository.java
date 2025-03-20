package com.supernova.ai.Repository;

import com.supernova.ai.Entity.RolesEntity;
import com.supernova.ai.Entity.TeamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamsRepository extends JpaRepository<TeamsEntity, Integer> {

    Optional<TeamsEntity> findByTeamName(String teamName);

    Optional<TeamsEntity> findById(Long id);
}
