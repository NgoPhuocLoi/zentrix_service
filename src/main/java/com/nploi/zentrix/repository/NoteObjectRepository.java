package com.nploi.zentrix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nploi.zentrix.entity.NoteObjectEntity;

public interface NoteObjectRepository extends JpaRepository<NoteObjectEntity, Long> {

    List<NoteObjectEntity> findByParentIsNull();
}
