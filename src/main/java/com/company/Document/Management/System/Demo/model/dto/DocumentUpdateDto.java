package com.company.Document.Management.System.Demo.model.dto;

import com.company.Document.Management.System.Demo.model.DocumentType;
import jakarta.validation.constraints.AssertTrue;

public class DocumentUpdateDto {
    private String name;
    private DocumentType type;

    public DocumentUpdateDto() {
    }

    public DocumentUpdateDto(String name, DocumentType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public DocumentType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    @AssertTrue(message = "At least one parameter must be present to update document")
    private boolean isAtLeastOnePresent(){
        return (name != null && !name.trim().isEmpty()) || type != null;
    }
}
