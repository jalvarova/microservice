package org.hta.service;

import org.hta.exceptions.DomainException;

import java.util.List;
import java.util.Optional;

public interface IUserService<T> {


        List<T> list(T i) throws DomainException;

        Optional<T> findById(Long id) throws DomainException;

        List<T> list() throws DomainException;

        Optional<T> save(T i) throws DomainException;

        Optional<T> update(T i) throws DomainException;

        Optional<T> delete(String userName) throws DomainException;

        Optional<T> delete(Long id) throws DomainException;

        Optional<T> findByUserName(String userName) throws DomainException;

}
