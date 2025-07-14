package com.nploi.zentrix.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note-tree")
public class NoteTreeResource {

    @GetMapping("/initial")
    public String getInitialNoteTree() {
        // This method should return the initial note tree structure
        return "Initial Note Tree Structure";
    }
}
