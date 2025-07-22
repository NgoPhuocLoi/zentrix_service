package com.nploi.zentrix.resources;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nploi.zentrix.dto.request.UpdateFileContentDto;
import com.nploi.zentrix.entity.FileContentEntity;
import com.nploi.zentrix.service.FileContentService;

@RestController
@RequestMapping("/api/file-contents")
public class FileContentResource {

    @Autowired
    private FileContentService fileContentService;

    @GetMapping("/{id}")
    public ResponseEntity<FileContentEntity> getFileContentById(
            @PathVariable("id") Long fileObjectId) throws BadRequestException {
        return ResponseEntity.ok(fileContentService.getFileContent(fileObjectId));
    }

    @PatchMapping("/{id}/content")
    public ResponseEntity<Void> updateFileContent(
            @PathVariable("id") Long fileObjectId,
            @RequestBody UpdateFileContentDto updateFileContentDto) {
        System.out.println(
                "Updating file content for ID: " + fileObjectId + " with content: " + updateFileContentDto.content());
        fileContentService.updateFileContent(fileObjectId, updateFileContentDto.content());
        return ResponseEntity.noContent().build();
    }
}
