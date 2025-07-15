package com.nploi.zentrix.dto.request;

import com.nploi.zentrix.enums.NoteObjectType;

public record CreateNoteObjectDto(String title, NoteObjectType type, Long parentId) {

    public CreateNoteObjectDto {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
    }

}
