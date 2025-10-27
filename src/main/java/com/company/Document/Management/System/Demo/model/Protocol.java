package com.company.Document.Management.System.Demo.model;

import com.company.Document.Management.System.Demo.model.dto.ProtocolDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
public class Protocol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long protocolId;
    private String createdBy;
    private LocalDateTime createdAt;
    private ProtocolState protocolState;
    @OneToMany(mappedBy = "protocol")
    private Set<Document> documents;

    public Protocol() {
    }

    public Protocol(String createdBy, LocalDateTime createdAt, ProtocolState protocolState, Set<Document> documents) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.protocolState = protocolState;
        this.documents = documents;
        for (Document document : this.documents) {
            document.setProtocol(this);
        }
    }

    public void addDocument(Document document) {
        documents.add(document);
        document.setProtocol(this);
    }

    public void addDocumentsInBulk(Collection<Document> documentsToAdd, boolean cleanBefore) {
        if (cleanBefore) documents.clear();

        for (Document document : documentsToAdd) {
            addDocument(document);
        }
    }

    public Long getProtocolId() {
        return protocolId;
    }

    public void setProtocolState(ProtocolState protocolState) {
        this.protocolState = protocolState;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ProtocolDto toProtocolDto() {
        return new ProtocolDto(protocolId, createdBy, createdAt, protocolState, documents);
    }
}
