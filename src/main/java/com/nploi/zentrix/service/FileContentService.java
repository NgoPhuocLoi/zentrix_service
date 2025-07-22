package com.nploi.zentrix.service;

import java.util.Objects;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.nploi.zentrix.entity.FileContentEntity;
import com.nploi.zentrix.repository.FileContentRepository;

@Service
public class FileContentService {

    @Autowired
    private FileContentRepository fileContentRepository;

    public FileContentEntity getFileContent(Long fileObjectId) throws BadRequestException {
        Preconditions.checkArgument(Objects.nonNull(fileObjectId), "File object ID must not be null");
        Optional<FileContentEntity> fileContentEntity = fileContentRepository.findByNoteObjectId(fileObjectId);
        if (fileContentEntity.isEmpty()) {
            throw new BadRequestException("File content not found for ID: " + fileObjectId);
        }
        return fileContentEntity.get();
    }

    @Transactional
    public void updateFileContent(Long fileObjectId, String newContent) {
        Preconditions.checkNotNull(fileObjectId, "File ID must not be null");
        fileContentRepository.updateContentByObjectId(fileObjectId, newContent);
    }
}
