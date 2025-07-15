package com.nploi.zentrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nploi.zentrix.entity.FileContentEntity;
import com.nploi.zentrix.repository.FileContentRepository;

@Service
public class FileContentService {

    @Autowired
    private FileContentRepository fileContentRepository;

    // public FileContentEntity createFileContent(Long noteObjectId, String content)
    // {
    // return fileContentRepository.save(new FileContentEntity(noteObjectId,
    // content)) != null;
    // }
}
