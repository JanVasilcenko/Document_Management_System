package com.company.Document.Management.System.Demo;

import com.company.Document.Management.System.Demo.exception.NotFoundException;
import com.company.Document.Management.System.Demo.model.Document;
import com.company.Document.Management.System.Demo.model.DocumentType;
import com.company.Document.Management.System.Demo.model.dto.DocumentCreateDto;
import com.company.Document.Management.System.Demo.model.dto.DocumentUpdateDto;
import com.company.Document.Management.System.Demo.persistence.DocumentRepository;
import com.company.Document.Management.System.Demo.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {
    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    private Document existing;

    private static final Long TEST_DOCUMENT_ID = 99L;

    @BeforeEach
    void setup(){
        existing = new Document("DocumentName", "Someone", LocalDateTime.now(), DocumentType.JPG);
        existing.setDocumentId(TEST_DOCUMENT_ID);
    }

    @Test
    void getDocument_Success(){
        //Arrange
        when(documentRepository.findById(TEST_DOCUMENT_ID)).thenReturn(Optional.of(existing));

        //Act
        Document result = documentService.getDocument(TEST_DOCUMENT_ID);

        //Assert
        assertThat(result).isSameAs(existing);
        verify(documentRepository).findById(TEST_DOCUMENT_ID);
        verifyNoMoreInteractions(documentRepository);
    }

    @Test
    void getDocument_NotFound(){
        //Arrange
        when(documentRepository.findById(TEST_DOCUMENT_ID)).thenReturn(Optional.empty());

        //Act + Assert
        assertThatThrownBy(() -> documentService.getDocument(TEST_DOCUMENT_ID))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void createDocument_Success(){
        //Arrange
        DocumentCreateDto documentCreateDto = new DocumentCreateDto("NewDocument", "John", DocumentType.PDF);
        Document toSave = documentCreateDto.toDocument();
        Document saved = new Document(toSave.getName(), toSave.getCreatedBy(), toSave.getCreatedAt(), toSave.getType());
        saved.setDocumentId(999L);

        when(documentRepository.save(any(Document.class))).thenReturn(saved);

        //Act
        Document result = documentService.createDocument(documentCreateDto);

        //Assert
        assertThat(result.getDocumentId()).isEqualTo(999L);
        assertThat(result.getName()).isEqualTo("NewDocument");
        assertThat(result.getType()).isEqualTo(DocumentType.PDF);
    }

    @Test
    void updateDocument_Success(){
        //Arrange
        when(documentRepository.findById(TEST_DOCUMENT_ID)).thenReturn(Optional.of(existing));

        DocumentUpdateDto documentUpdateDto = new DocumentUpdateDto();
        documentUpdateDto.setName("NewName");
        documentUpdateDto.setType(null);

        //Act
        documentService.updateDocument(TEST_DOCUMENT_ID, documentUpdateDto);

        //Assert
        assertThat(existing.getName()).isEqualTo("NewName");
        assertThat(existing.getType()).isEqualTo(DocumentType.JPG);

        verify(documentRepository).findById(TEST_DOCUMENT_ID);
        verify(documentRepository).save(existing);
    }

    @Test
    void updateDocument_NotFound(){
        //Arrange
        when(documentRepository.findById(TEST_DOCUMENT_ID)).thenReturn(Optional.empty());

        //Act + Assert
        assertThatThrownBy(()->documentService.updateDocument(TEST_DOCUMENT_ID, null))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void deleteDocument_Success(){
        //Arrange
        when(documentRepository.deleteByIdReturnCountAffected(TEST_DOCUMENT_ID)).thenReturn(1);

        //Act
        boolean result = documentService.deleteDocument(TEST_DOCUMENT_ID);

        //Assert
        assertThat(result).isTrue();
        verify(documentRepository).deleteByIdReturnCountAffected(TEST_DOCUMENT_ID);
    }

    @Test
    void deleteDocument_NotFound(){
        //Arrange
        when(documentRepository.deleteByIdReturnCountAffected(TEST_DOCUMENT_ID)).thenReturn(0);

        //Act
        boolean result = documentService.deleteDocument(TEST_DOCUMENT_ID);

        //Assert
        assertThat(result).isFalse();
        verify(documentRepository).deleteByIdReturnCountAffected(TEST_DOCUMENT_ID);
    }
}
