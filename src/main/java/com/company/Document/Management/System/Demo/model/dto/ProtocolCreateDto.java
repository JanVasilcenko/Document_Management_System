package com.company.Document.Management.System.Demo.model.dto;

import com.company.Document.Management.System.Demo.model.Document;
import com.company.Document.Management.System.Demo.model.Protocol;
import com.company.Document.Management.System.Demo.model.ProtocolState;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

public class ProtocolCreateDto {
    @NotEmpty(message = "Protocol should have at least one document")
    private List<Long> documentIds;
    private ProtocolState protocolState;

    public ProtocolCreateDto() {
    }

    public ProtocolCreateDto(List<Long> documentIds, ProtocolState protocolState) {
        this.documentIds = documentIds;
        this.protocolState = protocolState;
    }

    public List<Long> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<Long> documentIds) {
        this.documentIds = documentIds;
    }

    public ProtocolState getProtocolState() {
        return protocolState;
    }

    public void setProtocolState(ProtocolState protocolState) {
        this.protocolState = protocolState;
    }

    public Protocol toProtocol(List<Document> documents){
        return new Protocol(LocalDateTime.now(), protocolState, new HashSet<>(documents));
    }
}
