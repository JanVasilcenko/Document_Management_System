package com.company.Document.Management.System.Demo.persistence;

import com.company.Document.Management.System.Demo.model.Document;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    @Modifying
    @Query("DELETE FROM Document d WHERE d.documentId = :documentId")
    int deleteByIdReturnCountAffected(@Param("documentId") Long documentId);

    List<Document> findByProtocol_ProtocolId(Long protocolId);

    @Query("SELECT d.documentId FROM Document d WHERE d.documentId in :ids AND d.protocol IS NOT NULL AND d.protocol.protocolId <> :protocolId")
    List<Long> findConflictingIds(@Param("ids") Collection<Long> ids, @Param("protocolId") Long protocolId);
}
