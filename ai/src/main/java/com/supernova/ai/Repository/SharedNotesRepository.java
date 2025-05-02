package com.supernova.ai.Repository;

import com.supernova.ai.Entity.SharedNotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharedNotesRepository extends JpaRepository<SharedNotesEntity, Long> {
    List<SharedNotesEntity> findByReceiverId(Long id);
}
