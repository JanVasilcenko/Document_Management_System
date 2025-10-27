package com.company.Document.Management.System.Demo.controller;

import com.company.Document.Management.System.Demo.exception.NotFoundException;
import com.company.Document.Management.System.Demo.model.Document;
import com.company.Document.Management.System.Demo.model.dto.DocumentCreateDto;
import com.company.Document.Management.System.Demo.model.dto.DocumentDto;
import com.company.Document.Management.System.Demo.model.dto.DocumentUpdateDto;
import com.company.Document.Management.System.Demo.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

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
    public ResponseEntity editDocument(@PathVariable("id") Long documentId,
                                       @RequestBody @Valid DocumentUpdateDto documentUpdateDto) {
        documentService.updateDocument(documentId, documentUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable("id") Long documentId) {
        return documentService.deleteDocument(documentId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException (NotFoundException exception){
        return new ResponseEntity(Map.of("Error", exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
