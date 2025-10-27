package com.company.Document.Management.System.Demo.controller;

import com.company.Document.Management.System.Demo.exception.ConflictException;
import com.company.Document.Management.System.Demo.model.Protocol;
import com.company.Document.Management.System.Demo.model.dto.ProtocolChangeStateDto;
import com.company.Document.Management.System.Demo.model.dto.ProtocolCreateDto;
import com.company.Document.Management.System.Demo.model.dto.ProtocolDto;
import com.company.Document.Management.System.Demo.model.dto.ProtocolPutDto;
import com.company.Document.Management.System.Demo.service.ProtocolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/protocols")
public class ProtocolController {

    @Autowired
    private final ProtocolService protocolService;

    public ProtocolController(ProtocolService protocolService) {
        this.protocolService = protocolService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProtocolDto> getProtocol(@PathVariable("id") Long protocolId){
        return ResponseEntity.ok(protocolService.getProtocol(protocolId).toProtocolDto());
    }

    @PostMapping
    public ResponseEntity<ProtocolDto> createProtocol(@RequestBody @Valid ProtocolCreateDto protocolCreateDto) {
        Protocol createdProtocol = protocolService.createProtocol(protocolCreateDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProtocol.getProtocolId())
                .toUri();

        return ResponseEntity.created(location).body(createdProtocol.toProtocolDto());
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<ProtocolDto> editProtocol(@PathVariable("id") Long protocolId, @RequestBody @Valid ProtocolPutDto protocolPutDto) {
        Protocol updated = protocolService.editProtocol(protocolId, protocolPutDto);
        return ResponseEntity.ok(updated.toProtocolDto());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> editStateOfProtocol(@PathVariable("id") Long protocolId, @RequestBody ProtocolChangeStateDto protocolChangeStateDto) {
        return protocolService.editStateOfProtocol(protocolId, protocolChangeStateDto) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> handleConflictException(ConflictException exception) {
        return new ResponseEntity(Map.of("Error", exception.getMessage()), HttpStatus.CONFLICT);
    }
}
