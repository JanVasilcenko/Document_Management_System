package com.company.Document.Management.System.Demo.model;

import com.company.Document.Management.System.Demo.model.dto.DocumentDto;
import com.company.Document.Management.System.Demo.model.dto.DocumentUpdateDto;
import jakarta.persistence.*;

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
    @ManyToOne
    @JoinColumn(name = "protocolId")
    private Protocol protocol;

    public Document() {
    }

    public Document(String name, LocalDateTime createdAt, DocumentType type) {
        this.name = name;
        this.createdAt = createdAt;
        this.type = type;
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

    public boolean hasProtocol() {
        return protocol != null;
    }

    public boolean isAssignedToProtocol(Long protocolId) {
        return hasProtocol() && protocol.getProtocolId().equals(protocolId);
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
