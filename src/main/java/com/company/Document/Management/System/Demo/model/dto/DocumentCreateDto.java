package com.company.Document.Management.System.Demo.model.dto;

import com.company.Document.Management.System.Demo.model.Document;
import com.company.Document.Management.System.Demo.model.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DocumentCreateDto {
    @NotBlank(message = "Name is required to create a document")
    private String name;
    @NotNull(message = "Document type is required to create a document")
    private DocumentType type;

    public DocumentCreateDto() {
    }

    public DocumentCreateDto(String name, DocumentType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public Document toDocument() {
        return new Document(name, LocalDateTime.now(), type);
    }
}
