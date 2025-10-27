package com.company.Document.Management.System.Demo.controller;

import com.company.Document.Management.System.Demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    @Autowired
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public void createDocument() {
        //Request body with document create DTO
    }

    @PatchMapping
    public void editDocument() {
        //Request the id of the document
        //Edit the things present in the update DTO
    }

    @DeleteMapping
    public void deleteDocument() {
        //Delete the document with specified ID
    }
}
