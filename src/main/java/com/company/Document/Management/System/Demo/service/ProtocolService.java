package com.company.Document.Management.System.Demo.service;

import com.company.Document.Management.System.Demo.exception.ConflictException;
import com.company.Document.Management.System.Demo.exception.NotFoundException;
import com.company.Document.Management.System.Demo.model.Document;
import com.company.Document.Management.System.Demo.model.Protocol;
import com.company.Document.Management.System.Demo.model.dto.ProtocolChangeStateDto;
import com.company.Document.Management.System.Demo.model.dto.ProtocolCreateDto;
import com.company.Document.Management.System.Demo.model.dto.ProtocolPutDto;
import com.company.Document.Management.System.Demo.persistence.DocumentRepository;
import com.company.Document.Management.System.Demo.persistence.ProtocolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProtocolService {
    @Autowired
    private final ProtocolRepository protocolRepository;

    @Autowired
    private final DocumentRepository documentRepository;

    public ProtocolService(ProtocolRepository protocolRepository, DocumentRepository documentRepository) {
        this.protocolRepository = protocolRepository;
        this.documentRepository = documentRepository;
    }

    public Protocol getProtocol(Long protocolId) {
        return protocolRepository.findById(protocolId).orElseThrow(() -> new NotFoundException("Protocol with specified id does not exist"));
    }

    @Transactional
    public Protocol createProtocol(ProtocolCreateDto protocolCreateDto) {
        List<Document> documents = new ArrayList<>();
        documentRepository.findAllById(protocolCreateDto.getDocumentIds()).forEach(documents::add);

        List<Long> foundIds = documents.stream()
                .map(Document::getDocumentId)
                .toList();

        if (protocolCreateDto.getDocumentIds().size() != documents.size()) {
            throw new NotFoundException("Documents with ids: " + getMissingIds(protocolCreateDto.getDocumentIds(), foundIds) + " not found");
        }

        List<Long> documentIdsWithProtocol = getDocumentsWithProtocolAssigned(documents);
        if (!documentIdsWithProtocol.isEmpty()) {
            throw new ConflictException("Documents with ids: " + documentIdsWithProtocol + " already have a protocol assigned");
        }

        Protocol newProtocol = protocolCreateDto.toProtocol(documents);
        return protocolRepository.save(newProtocol);
    }

    @Transactional
    public Protocol editProtocol(Long protocolId, ProtocolPutDto protocolPutDto) {
        Protocol protocol = protocolRepository.findById(protocolId).orElseThrow(() -> new NotFoundException("Protocol with specified id not found"));

        List<Document> requestedDocuments = new ArrayList<>();
        documentRepository.findAllById(protocolPutDto.getDocumentIds()).forEach(requestedDocuments::add);

        List<Long> missingIds = getMissingIds(protocolPutDto.getDocumentIds(), requestedDocuments.stream().map(Document::getDocumentId).toList());

        if (!missingIds.isEmpty()) {
            throw new NotFoundException("Documents with ids: " + missingIds + " not found");
        }

        List<Long> documentIdsWithProtocol = documentRepository.findConflictingIds(protocolPutDto.getDocumentIds(), protocolId);
        if (!documentIdsWithProtocol.isEmpty()) {
            throw new ConflictException("Documents with ids: " + documentIdsWithProtocol + " have different protocol assigned");
        }

        List<Document> documentsWithProtocolId = documentRepository.findByProtocol_ProtocolId(protocolId);
        for (Document document : documentsWithProtocolId) {
            if (!protocolPutDto.getDocumentIds().contains(document.getDocumentId())) {
                document.setProtocol(null);
            }
        }

        protocol.addDocumentsInBulk(requestedDocuments, true);
        protocol.setCreatedBy(protocolPutDto.getCreatedBy());
        protocol.setProtocolState(protocolPutDto.getProtocolState());

        return protocolRepository.save(protocol);
    }

    @Transactional
    public boolean editStateOfProtocol(long protocolId, ProtocolChangeStateDto protocolChangeStateDto) {
        return protocolRepository.updateByIdReturnCountAffected(protocolId, protocolChangeStateDto.getProtocolState()) > 0;
    }

    private List<Long> getDocumentsWithDifferentProtocol(List<Document> documents, Long protocolId) {
        return documents.stream().filter(d -> !d.isAssignedToProtocol(protocolId)).map(Document::getDocumentId).toList();
    }

    private List<Long> getDocumentsWithProtocolAssigned(List<Document> documents) {
        return documents.stream().filter(Document::hasProtocol).map(Document::getDocumentId).toList();
    }

    private List<Long> getMissingIds(List<Long> requestedIds, List<Long> foundIds) {
        return requestedIds.stream().filter(i -> !foundIds.contains(i)).toList();
    }
}
