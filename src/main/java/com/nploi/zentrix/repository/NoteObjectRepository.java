package com.nploi.zentrix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nploi.zentrix.entity.NoteObjectEntity;

public interface NoteObjectRepository extends JpaRepository<NoteObjectEntity, Long> {

    List<NoteObjectEntity> findByParentIsNull();

    @Query("SELECT n FROM NoteObjectEntity n WHERE n.parent.id = :parentId")
    List<NoteObjectEntity> findByParentId(Long parentId);
}
