package com.company.Document.Management.System.Demo.model.dto;

import com.company.Document.Management.System.Demo.model.ProtocolState;
import jakarta.validation.constraints.NotNull;

public class ProtocolChangeStateDto {
    @NotNull(message = "Protocol state required for updating protocol state")
    private ProtocolState protocolState;

    public ProtocolChangeStateDto() {
    }

    public ProtocolChangeStateDto(ProtocolState protocolState) {
        this.protocolState = protocolState;
    }

    public ProtocolState getProtocolState() {
        return protocolState;
    }

    public void setProtocolState(ProtocolState protocolState) {
        this.protocolState = protocolState;
    }
}
