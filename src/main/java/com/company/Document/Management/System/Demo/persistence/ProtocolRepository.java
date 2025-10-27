package com.company.Document.Management.System.Demo.persistence;

import com.company.Document.Management.System.Demo.model.Protocol;
import com.company.Document.Management.System.Demo.model.ProtocolState;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProtocolRepository extends CrudRepository<Protocol, Long> {
    @Modifying
    @Query("UPDATE Protocol p SET p.protocolState = :protocolState WHERE  p.protocolId = :protocolId")
    int updateByIdReturnCountAffected(@Param("protocolId") Long protocolId, @Param("protocolState") ProtocolState protocolState);
}
