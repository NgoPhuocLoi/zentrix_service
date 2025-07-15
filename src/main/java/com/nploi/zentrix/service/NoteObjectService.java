package com.nploi.zentrix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nploi.zentrix.converter.NoteObjectEntityConverter;
import com.nploi.zentrix.dto.request.CreateNoteObjectDto;
import com.nploi.zentrix.entity.FileContentEntity;
import com.nploi.zentrix.entity.NoteObjectEntity;
import com.nploi.zentrix.enums.NoteObjectType;
import com.nploi.zentrix.repository.FileContentRepository;
import com.nploi.zentrix.repository.NoteObjectRepository;

@Service
public class NoteObjectService {

    @Autowired
    private NoteObjectRepository noteObjectRepository;

    @Autowired
    private FileContentRepository fileContentRepository;

    public List<NoteObjectEntity> findAll() {
        return noteObjectRepository.findAll();
    }

    public List<NoteObjectEntity> findRootObjects() {
        return noteObjectRepository.findByParentIsNull();
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
}
