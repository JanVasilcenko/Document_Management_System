package com.company.Document.Management.System.Demo.model.dto;

import com.company.Document.Management.System.Demo.model.Document;
import com.company.Document.Management.System.Demo.model.ProtocolState;

import java.time.LocalDateTime;
import java.util.Set;

public class ProtocolDto {
    private Long protocolId;
    private String createdBy;
    private LocalDateTime createdAt;
    private ProtocolState protocolState;
    private Set<Document> documents;

    public ProtocolDto() {
    }

    public ProtocolDto(Long protocolId, String createdBy, LocalDateTime createdAt, ProtocolState protocolState, Set<Document> documents) {
        this.protocolId = protocolId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.protocolState = protocolState;
        this.documents = documents;
    }

    public Long getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ProtocolState getProtocolState() {
        return protocolState;
    }

    public void setProtocolState(ProtocolState protocolState) {
        this.protocolState = protocolState;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }
}
