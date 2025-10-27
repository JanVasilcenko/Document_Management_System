package com.company.Document.Management.System.Demo.model.dto;

import com.company.Document.Management.System.Demo.model.ProtocolState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ProtocolPutDto {
    @NotBlank(message = "Author of protocol must be provided")
    private String createdBy;
    @NotNull(message = "Protocol state is required")
    private ProtocolState protocolState;
    @NotEmpty(message = "Protocol must hold at least one document")
    private List<Long> documentIds;

    public ProtocolPutDto() {
    }

    public ProtocolPutDto(String createdBy, ProtocolState protocolState, List<Long> documentIds) {
        this.createdBy = createdBy;
        this.protocolState = protocolState;
        this.documentIds = documentIds;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
