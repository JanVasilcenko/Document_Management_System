package com.company.Document.Management.System.Demo.persistence;

import com.company.Document.Management.System.Demo.model.Document;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    @Modifying
    @Query("DELETE FROM Document d where d.documentId = :documentId")
    int deleteByIdReturnCountAffected(@Param("documentId") Long documentId);
}
