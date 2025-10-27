package com.company.Document.Management.System.Demo.model.dto;

import com.company.Document.Management.System.Demo.model.Document;
import com.company.Document.Management.System.Demo.model.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DocumentCreateDto {
    @NotBlank(message = "Name is required to create a document")
    private String name;
    @NotBlank(message = "Author is required to create a document")
    private String createdBy;
    @NotNull(message = "Document type is required to create a document")
    private DocumentType type;

    public DocumentCreateDto() {
    }

    public DocumentCreateDto(String name, String createdBy, DocumentType type) {
        this.name = name;
        this.createdBy = createdBy;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public Document toDocument() {
        return new Document(name, createdBy, LocalDateTime.now(), type);
    }
}
