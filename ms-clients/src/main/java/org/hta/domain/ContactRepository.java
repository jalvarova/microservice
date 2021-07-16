package org.hta.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.hta.domain.entity.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

}
