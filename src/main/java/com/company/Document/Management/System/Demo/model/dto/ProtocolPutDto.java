package com.company.Document.Management.System.Demo.model.dto;

import com.company.Document.Management.System.Demo.model.ProtocolState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ProtocolPutDto {
    @NotNull(message = "Protocol state is required")
    private ProtocolState protocolState;
    @NotEmpty(message = "Protocol must hold at least one document")
    private List<Long> documentIds;

    public ProtocolPutDto() {
    }

    public ProtocolPutDto(ProtocolState protocolState, List<Long> documentIds) {
        this.protocolState = protocolState;
        this.documentIds = documentIds;
    }

    public ProtocolState getProtocolState() {
        return protocolState;
    }

    public void setProtocolState(ProtocolState protocolState) {
        this.protocolState = protocolState;
    }

    public List<Long> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<Long> documentIds) {
        this.documentIds = documentIds;
    }
}
