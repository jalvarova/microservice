package org.hta.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.hta.domain.entity.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("select c from Client c where c.state = true and upper(c.businessName) like :businessName")
    List<Client> list(@Param("businessName") String businessName);

    @Query("select c from Client c where c.document = :document and c.state = true")
    Optional<Client> findDocumentNumber(@Param("document") String document);

    @Query("select c from Client c where c.state = true")
    List<Client> listAll();

    @Modifying
    @Query("update Client c  set c.state = false  where c.clientId = :clientId")
    void deleteLogic(@Param("clientId") Long clientId);
}
