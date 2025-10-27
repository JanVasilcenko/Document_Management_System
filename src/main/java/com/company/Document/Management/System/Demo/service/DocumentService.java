package com.company.Document.Management.System.Demo.service;

import com.company.Document.Management.System.Demo.exception.NotFoundException;
import com.company.Document.Management.System.Demo.model.Document;
import com.company.Document.Management.System.Demo.model.dto.DocumentCreateDto;
import com.company.Document.Management.System.Demo.model.dto.DocumentUpdateDto;
import com.company.Document.Management.System.Demo.persistence.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentService {
    @Autowired
    private final DocumentRepository documentRepository;
    @Autowired
    private final UserService userService;
    private static final String DOCUMENT_NOT_FOUND_ERROR_MESSAGE = "Document with specified id does not exist";

    public DocumentService(DocumentRepository documentRepository, UserService userService) {
        this.documentRepository = documentRepository;
        this.userService = userService;
    }

    public Document getDocument(Long documentId){
        return documentRepository.findById(documentId).orElseThrow(() -> new NotFoundException(DOCUMENT_NOT_FOUND_ERROR_MESSAGE));
    }

    public Document createDocument(DocumentCreateDto documentCreateDto) {
        Document newDocument = documentCreateDto.toDocument();
        newDocument.setCreatedBy(userService.getCurrentUsername());
        return documentRepository.save(newDocument);
    }

    @Transactional
    public Document updateDocument(Long updatedDocumentId, DocumentUpdateDto documentUpdateDto) {
        Document existingDocument = documentRepository.findById(updatedDocumentId).orElseThrow(() -> new NotFoundException(DOCUMENT_NOT_FOUND_ERROR_MESSAGE));
        existingDocument.updatePresentParametersFromUpdateDto(documentUpdateDto);
        return documentRepository.save(existingDocument);
    }

    @Transactional
    public boolean deleteDocument(Long documentToDelete) {
        return documentRepository.deleteByIdReturnCountAffected(documentToDelete) > 0;
    }
}
