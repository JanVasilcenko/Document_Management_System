package com.company.Document.Management.System.Demo;

import com.company.Document.Management.System.Demo.exception.ConflictException;
import com.company.Document.Management.System.Demo.exception.NotFoundException;
import com.company.Document.Management.System.Demo.model.Document;
import com.company.Document.Management.System.Demo.model.Protocol;
import com.company.Document.Management.System.Demo.model.ProtocolState;
import com.company.Document.Management.System.Demo.model.dto.ProtocolChangeStateDto;
import com.company.Document.Management.System.Demo.model.dto.ProtocolCreateDto;
import com.company.Document.Management.System.Demo.model.dto.ProtocolPutDto;
import com.company.Document.Management.System.Demo.persistence.DocumentRepository;
import com.company.Document.Management.System.Demo.persistence.ProtocolRepository;
import com.company.Document.Management.System.Demo.service.ProtocolService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProtocolServiceTest {
    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private ProtocolRepository protocolRepository;

    @InjectMocks
    private ProtocolService protocolService;

    private static final Long TEST_PROTOCOL_ID = 99L;

    @Test
    void getProtocol_Success() {
        //Arrange
        Protocol protocol = protocol(TEST_PROTOCOL_ID);
        when(protocolRepository.findById(TEST_PROTOCOL_ID)).thenReturn(Optional.of(protocol));

        //Act
        Protocol result = protocolService.getProtocol(TEST_PROTOCOL_ID);

        //Assert
        assertThat(result).isSameAs(protocol);
        verify(protocolRepository).findById(TEST_PROTOCOL_ID);
    }

    @Test
    void getProtocol_NotFound() {
        //Arrange
        when(protocolRepository.findById(TEST_PROTOCOL_ID)).thenReturn(Optional.empty());

        //Act + Assert
        assertThatThrownBy(() -> protocolService.getProtocol(TEST_PROTOCOL_ID))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void createProtocol_Success() {
        //Arrange
        ProtocolCreateDto protocolCreateDto = mock(ProtocolCreateDto.class);
        when(protocolCreateDto.getDocumentIds()).thenReturn(List.of(1L, 2L));

        Document documentOne = document(1L);
        Document documentTwo = document(2L);
        when(documentRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(documentOne, documentTwo));

        Protocol protocol = protocol(TEST_PROTOCOL_ID);
        when(protocolCreateDto.toProtocol(any())).thenReturn(protocol);
        when(protocolRepository.save(protocol)).thenReturn(protocol);

        //Act
        protocolService.createProtocol(protocolCreateDto);

        //Assert
        verify(protocolRepository).save(protocol);
    }

    @Test
    void createProtocol_NotFound() {
        //Arrange
        ProtocolCreateDto protocolCreateDto = mock(ProtocolCreateDto.class);
        when(protocolCreateDto.getDocumentIds()).thenReturn(List.of(1L, 2L));

        Document documentOne = document(1L);
        when(documentRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(documentOne));

        //Act + Assert
        assertThatThrownBy(() -> protocolService.createProtocol(protocolCreateDto)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void createProtocol_Conflict() {
        //Arrange
        ProtocolCreateDto protocolCreateDto = mock(ProtocolCreateDto.class);
        when(protocolCreateDto.getDocumentIds()).thenReturn(List.of(1L, 2L));

        Document documentOne = document(1L);
        Document documentTwo = document(2L);
        documentTwo.setProtocol(new Protocol());

        when(documentRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(documentOne, documentTwo));

        //Act + Assert
        assertThatThrownBy(() -> protocolService.createProtocol(protocolCreateDto)).isInstanceOf(ConflictException.class);
    }

    @Test
    void updateProtocol_Success() {
        //Arrange
        Protocol existing = new Protocol("First", LocalDateTime.now(), ProtocolState.NEW, new HashSet<>());
        when(protocolRepository.findById(TEST_PROTOCOL_ID)).thenReturn(Optional.of(existing));

        Document documentOne = document(1);
        Document documentTwo = document(2);
        when(documentRepository.findByProtocol_ProtocolId(TEST_PROTOCOL_ID)).thenReturn(List.of(documentOne, documentTwo));

        ProtocolPutDto protocolPutDto = mock(ProtocolPutDto.class);
        when(protocolPutDto.getDocumentIds()).thenReturn(List.of(1L));
        when(protocolPutDto.getCreatedBy()).thenReturn("New");
        when(protocolPutDto.getProtocolState()).thenReturn(ProtocolState.PREPARE_FOR_SHIPMENT);

        when(documentRepository.findAllById(List.of(1L))).thenReturn(List.of(documentOne));
        when(documentRepository.findConflictingIds(List.of(1L), TEST_PROTOCOL_ID)).thenReturn(List.of());

        when(protocolRepository.save(existing)).thenReturn(existing);

        //Act
        Protocol result = protocolService.editProtocol(TEST_PROTOCOL_ID, protocolPutDto);

        //Assert
        assertThat(documentTwo.hasProtocol()).isFalse();
        assertThat(documentOne.hasProtocol()).isTrue();

        assertThat(result.getCreatedBy()).isEqualTo("New");
        assertThat(result.getProtocolState()).isEqualTo(ProtocolState.PREPARE_FOR_SHIPMENT);
        verify(protocolRepository).save(existing);
    }

    @Test
    void updateProtocol_Protocol_NotFound() {
        //Arrange
        when(protocolRepository.findById(TEST_PROTOCOL_ID)).thenReturn(Optional.empty());
        ProtocolPutDto dto = mock(ProtocolPutDto.class);

        //Act + Assert
        assertThatThrownBy(() -> protocolService.editProtocol(TEST_PROTOCOL_ID, dto)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateProtocol_Ids_NotFound() {
        //Arrange
        when(protocolRepository.findById(TEST_PROTOCOL_ID)).thenReturn(Optional.of(protocol(TEST_PROTOCOL_ID)));

        ProtocolPutDto protocolPutDto = mock(ProtocolPutDto.class);
        when(protocolPutDto.getDocumentIds()).thenReturn(List.of(1L, 2L));
        when(documentRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(document(1L)));

        //Act + Assert
        assertThatThrownBy(() -> protocolService.editProtocol(TEST_PROTOCOL_ID, protocolPutDto)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateProtocol_Conflict() {
        //Arrange
        when(protocolRepository.findById(TEST_PROTOCOL_ID)).thenReturn(Optional.of(protocol(TEST_PROTOCOL_ID)));

        ProtocolPutDto protocolPutDto = mock(ProtocolPutDto.class);
        when(protocolPutDto.getDocumentIds()).thenReturn(List.of(1L, 2L));

        Iterable<Document> documents = List.of(document(1L), document(2L));
        when(documentRepository.findAllById(any())).thenReturn(documents);

        when(documentRepository.findConflictingIds(List.of(1L, 2L), TEST_PROTOCOL_ID)).thenReturn(List.of(2L));

        //Act + Assert
        assertThatThrownBy(() -> protocolService.editProtocol(TEST_PROTOCOL_ID, protocolPutDto))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    void editStateOfProtocol_Success() {
        //Arrange
        ProtocolChangeStateDto protocolChangeStateDto = mock(ProtocolChangeStateDto.class);
        when(protocolChangeStateDto.getProtocolState()).thenReturn(ProtocolState.PREPARE_FOR_SHIPMENT);
        when(protocolRepository.updateByIdReturnCountAffected(TEST_PROTOCOL_ID, ProtocolState.PREPARE_FOR_SHIPMENT)).thenReturn(1);

        //Act
        boolean ok = protocolService.editStateOfProtocol(TEST_PROTOCOL_ID, protocolChangeStateDto);

        //Assert
        assertThat(ok).isTrue();
    }

    @Test
    void editStateOfProtocol_NotFound() {
        //Arrange
        ProtocolChangeStateDto protocolChangeStateDto = mock(ProtocolChangeStateDto.class);
        when(protocolChangeStateDto.getProtocolState()).thenReturn(ProtocolState.PREPARE_FOR_SHIPMENT);
        when(protocolRepository.updateByIdReturnCountAffected(TEST_PROTOCOL_ID, ProtocolState.PREPARE_FOR_SHIPMENT)).thenReturn(0);

        //Act
        boolean ok = protocolService.editStateOfProtocol(TEST_PROTOCOL_ID, protocolChangeStateDto);

        //Assert
        assertThat(ok).isFalse();
    }

    private Protocol protocol(long id) {
        Protocol protocol = new Protocol();
        protocol.setProtocolId(id);
        protocol.setCreatedBy("Test");
        protocol.setCreatedAt(LocalDateTime.now());
        protocol.setProtocolState(ProtocolState.CANCELLED);
        return protocol;
    }

    private Document document(long id) {
        Document document = new Document();
        document.setDocumentId(id);
        document.setName("Name");
        document.setCreatedAt(LocalDateTime.now());
        document.setCreatedBy("Test");
        return document;
    }
}
