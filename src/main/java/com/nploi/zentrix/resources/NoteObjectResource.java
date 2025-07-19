package com.nploi.zentrix.resources;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nploi.zentrix.dto.request.CreateNoteObjectDto;
import com.nploi.zentrix.entity.NoteObjectEntity;
import com.nploi.zentrix.service.NoteObjectService;

@RestController
@RequestMapping("/api/note-objects")
public class NoteObjectResource {

    @Autowired
    private NoteObjectService noteObjectService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteObjectEntity> createNoteObject(@RequestBody CreateNoteObjectDto createNoteObjectDto) {
        return ResponseEntity.ok(noteObjectService.createNoteObject(createNoteObjectDto));
    }

    @GetMapping
    public ResponseEntity<List<NoteObjectEntity>> find(
            @RequestParam(value = "root", required = false, defaultValue = "false") boolean root,
            @RequestParam(value = "parent-id", required = false) Long parentId) {
        if (root) {
            return ResponseEntity.ok(noteObjectService.findRootObjects());
        }

        if (Objects.nonNull(parentId)) {
            return ResponseEntity.ok(noteObjectService.findByParentId(parentId));
        }
        return ResponseEntity.ok(noteObjectService.findAll());
    }
}
