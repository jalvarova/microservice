package org.hta.service;

import org.hta.exceptions.DomainException;

import java.util.List;
import java.util.Optional;

public interface IClientService<T> {

    List<T> list(T i) throws DomainException;

    Optional<T> findById(Long id) throws DomainException;

    List<T> list() throws DomainException;

    Optional<T> save(T i) throws DomainException;

    Optional<T> update(T i) throws DomainException;

    Optional<T> delete(String document) throws DomainException;

    Optional<T> delete(Long id) throws DomainException;

    Optional<T> findByDocument(String document) throws DomainException;
}
