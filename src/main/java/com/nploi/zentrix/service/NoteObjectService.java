package com.nploi.zentrix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.nploi.zentrix.converter.NoteObjectEntityConverter;
import com.nploi.zentrix.dto.request.CreateNoteObjectDto;
import com.nploi.zentrix.entity.FileContentEntity;
import com.nploi.zentrix.entity.NoteObjectEntity;
import com.nploi.zentrix.enums.NoteObjectType;
import com.nploi.zentrix.repository.FileContentRepository;
import com.nploi.zentrix.repository.NoteObjectRepository;
import com.nploi.zentrix.utils.NoteObjectJsonPatch;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoteObjectService {

    @Autowired
    private NoteObjectRepository noteObjectRepository;

    @Autowired
    private FileContentRepository fileContentRepository;

    public List<NoteObjectEntity> findAll() {
        return noteObjectRepository.findAll();
    }

    public List<NoteObjectEntity> findRootObjects() {
        return noteObjectRepository.findByParentIsNullOrderByCreatedAt();
    }

    public NoteObjectEntity findById(Long id) {
        return noteObjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NoteObject with ID " + id + " not found."));
    }

    public List<NoteObjectEntity> findByParentId(Long parentId) {
        if (parentId == null) {
            log.warn("Parent ID is null, returning empty list.");
            return List.of();
        }
        log.info("Finding note objects by parent ID: {}", parentId);
        return noteObjectRepository.findByParentIdOrderByCreatedAt(parentId);
    }

    public NoteObjectEntity createNoteObject(CreateNoteObjectDto createNoteObjectDto) {

        NoteObjectEntity noteObjectEntity = NoteObjectEntityConverter.fromDto(createNoteObjectDto);
        NoteObjectEntity savedNoteObject = noteObjectRepository.save(noteObjectEntity);
        createDefaultFileContentIfNeeded(noteObjectEntity);
        return savedNoteObject;
    }

    private void createDefaultFileContentIfNeeded(NoteObjectEntity noteObjectEntity) {
        if (noteObjectEntity != null && noteObjectEntity.getType() == NoteObjectType.FILE) {
            FileContentEntity fileContentEntity = FileContentEntity.builder()
                    .noteObject(noteObjectEntity)
                    .content("")
                    .build();
            fileContentRepository.save(fileContentEntity);
        }
    }

    public NoteObjectEntity partialUpdateNoteObject(Long id, JsonPatch patch)
            throws JsonProcessingException, IllegalArgumentException, JsonPatchException {

        NoteObjectEntity existingNoteObject = noteObjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NoteObject with ID " + id + " not found."));
        NoteObjectEntity patchedNoteObject = NoteObjectJsonPatch.applyPatch(patch, existingNoteObject);
        return noteObjectRepository.save(patchedNoteObject);
    }

    public void deleteNoteObject(Long id) {
        NoteObjectEntity noteObjectEntity = noteObjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NoteObject with ID " + id + " not found."));
        noteObjectRepository.delete(noteObjectEntity);
        log.info("Deleted NoteObject with ID: {}", id);
    }
}
