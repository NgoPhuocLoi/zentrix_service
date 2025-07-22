package com.nploi.zentrix.utils;

import com.nploi.zentrix.entity.NoteObjectEntity;
import com.nploi.zentrix.enums.NoteObjectType;

public class NoteObjectUtil {
    public static boolean isFileType(NoteObjectEntity noteObjectEntity) {
        return noteObjectEntity != null && noteObjectEntity.getType() == NoteObjectType.FILE;
    }
}
