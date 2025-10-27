package com.company.Document.Management.System.Demo.persistence;

import com.company.Document.Management.System.Demo.model.Document;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Long, Document> {
}
