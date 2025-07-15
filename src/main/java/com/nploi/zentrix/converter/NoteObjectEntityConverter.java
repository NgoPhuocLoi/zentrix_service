package com.nploi.zentrix.converter;

import com.nploi.zentrix.dto.request.CreateNoteObjectDto;
import com.nploi.zentrix.entity.NoteObjectEntity;

public class NoteObjectEntityConverter {

    public static NoteObjectEntity fromId(Long parentId) {
        NoteObjectEntity noteObjectEntity = new NoteObjectEntity();
        noteObjectEntity.setId(parentId);
        return noteObjectEntity;
    }

    public static NoteObjectEntity fromDto(CreateNoteObjectDto createNoteObjectDto) {
        NoteObjectEntity noteObjectEntity = new NoteObjectEntity();
        noteObjectEntity.setTitle(createNoteObjectDto.title());
        noteObjectEntity.setType(createNoteObjectDto.type());
        if (createNoteObjectDto.parentId() != null) {
            noteObjectEntity.setParent(fromId(createNoteObjectDto.parentId()));
        }
        return noteObjectEntity;
    }
}
