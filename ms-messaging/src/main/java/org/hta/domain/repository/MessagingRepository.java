package org.hta.domain.repository;

import org.hta.domain.document.MessagingDocument;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MessagingRepository extends ReactiveCrudRepository<MessagingDocument, String> {

    Flux<MessagingDocument> findAllByState(Boolean state);

    Flux<MessagingDocument> findAllByEmailTo(String emailTo);

    Mono<MessagingDocument> findByOperationNumber(String operation);

}
