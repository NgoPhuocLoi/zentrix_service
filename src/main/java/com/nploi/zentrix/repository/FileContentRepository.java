package com.nploi.zentrix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nploi.zentrix.entity.FileContentEntity;

public interface FileContentRepository extends JpaRepository<FileContentEntity, Long> {

    @Query("SELECT f FROM FileContentEntity f WHERE f.noteObject.id = :noteObjectId")
    Optional<FileContentEntity> findByNoteObjectId(Long noteObjectId);
}
