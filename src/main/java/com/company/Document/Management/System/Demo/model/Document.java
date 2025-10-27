package com.company.Document.Management.System.Demo.model;

import com.company.Document.Management.System.Demo.model.dto.DocumentDto;
import com.company.Document.Management.System.Demo.model.dto.DocumentUpdateDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long documentId;
    private String name;
    private String createdBy;
    private LocalDateTime createdAt;
    private DocumentType type;

    protected Document() {
    }

    public Document(String name, String createdBy, LocalDateTime createdAt, DocumentType type) {
        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.type = type;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public DocumentType getType() {
        return type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updatePresentParametersFromUpdateDto(DocumentUpdateDto documentUpdateDto) {
        if (documentUpdateDto.getName() != null && !documentUpdateDto.getName().trim().isEmpty()) {
            name = documentUpdateDto.getName();
        }

        if (documentUpdateDto.getType() != null) {
            type = documentUpdateDto.getType();
        }
    }

    public DocumentDto toDocumentDto() {
        return new DocumentDto(documentId, name, type, createdAt, createdBy);
    }
}
