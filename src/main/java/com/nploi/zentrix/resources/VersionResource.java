package com.nploi.zentrix.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nploi.zentrix.dto.response.Version;

@RestController
public class VersionResource {

    @GetMapping("/api/version")
    public ResponseEntity<Version> getVersion() {
        Version version = Version.builder()
                .version("1.0.1")
                .build();
        return ResponseEntity.ok(version);
    }
}
