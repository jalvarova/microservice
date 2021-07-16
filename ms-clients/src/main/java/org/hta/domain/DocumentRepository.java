package org.hta.domain;

import org.hta.domain.entity.DocumentType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<DocumentType, Long> {

    @Query("select d from DocumentType d where d.type = :type")
    DocumentType findByType(String type);
}
