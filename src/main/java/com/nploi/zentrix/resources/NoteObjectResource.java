package com.nploi.zentrix.resources;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
            @RequestParam(value = "parentId", required = false) Long parentId,
            @RequestParam(value = "id", required = false) Long id) {
        if (Objects.nonNull(id)) {
            return ResponseEntity.ok(List.of(noteObjectService.findById(id)));
        }

        if (root) {
            return ResponseEntity.ok(noteObjectService.findRootObjects());
        }

        if (Objects.nonNull(parentId)) {
            return ResponseEntity.ok(noteObjectService.findByParentId(parentId));
        }
        return ResponseEntity.ok(noteObjectService.findAll());
    }

    @PatchMapping(path = "{id}", consumes = "application/json-patch+json")
    public ResponseEntity<NoteObjectEntity> patchNoteObject(
            @RequestBody JsonPatch patch,
            @PathVariable Long id) throws JsonProcessingException, IllegalArgumentException, JsonPatchException {
        return ResponseEntity.ok(noteObjectService.partialUpdateNoteObject(id, patch));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteNoteObject(@PathVariable Long id) {
        noteObjectService.deleteNoteObject(id);
        return ResponseEntity.noContent().build();
    }
}
