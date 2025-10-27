package com.company.Document.Management.System.Demo.controller;

import com.company.Document.Management.System.Demo.model.Document;
import com.company.Document.Management.System.Demo.model.dto.DocumentCreateDto;
import com.company.Document.Management.System.Demo.model.dto.DocumentDto;
import com.company.Document.Management.System.Demo.model.dto.DocumentUpdateDto;
import com.company.Document.Management.System.Demo.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    @Autowired
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getDocument(@PathVariable("id") Long documentId){
        Document foundDocument = documentService.getDocument(documentId);
        return ResponseEntity.ok().body(foundDocument.toDocumentDto());
    }


    @PostMapping()
    public ResponseEntity<DocumentDto> createDocument(@RequestBody @Valid DocumentCreateDto documentCreateDto) {
        Document createdDocument = documentService.createDocument(documentCreateDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDocument.getDocumentId())
                .toUri();

        return ResponseEntity.created(location).body(createdDocument.toDocumentDto());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DocumentDto> editDocument(@PathVariable("id") Long documentId,
                                       @RequestBody @Valid DocumentUpdateDto documentUpdateDto) {
        Document updated = documentService.updateDocument(documentId, documentUpdateDto);
        return ResponseEntity.ok(updated.toDocumentDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable("id") Long documentId) {
        return documentService.deleteDocument(documentId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
