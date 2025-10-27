package com.company.Document.Management.System.Demo.model.dto;

import com.company.Document.Management.System.Demo.model.DocumentType;

import java.time.LocalDateTime;

public class DocumentDto {
    private Long documentId;
    private String name;
    private DocumentType documentType;
    private LocalDateTime createdAt;
    private String createdBy;

    public DocumentDto() {
    }

    public DocumentDto(Long documentId, String name, DocumentType documentType, LocalDateTime createdAt, String createdBy) {
        this.documentId = documentId;
        this.name = name;
        this.documentType = documentType;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
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

    public void setName(String name) {
        this.name = name;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
