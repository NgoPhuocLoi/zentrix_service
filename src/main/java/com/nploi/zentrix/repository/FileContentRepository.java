package com.nploi.zentrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nploi.zentrix.entity.FileContentEntity;

public interface FileContentRepository extends JpaRepository<FileContentEntity, Long> {

    // Additional query methods can be defined here if needed

}
