package org.hta.service;

import lombok.extern.slf4j.Slf4j;
import org.hta.domain.entity.DocumentType;
import org.hta.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hta.domain.entity.Client;
import org.hta.domain.ClientRepository;
import org.hta.domain.DocumentRepository;
import org.hta.dto.ClientDTO;
import org.hta.mappers.CustomerMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hta.util.ClientUtil.build;
import static org.hta.util.ClientUtil.validateNotFound;

@Slf4j
@Service
public class ClientService implements IClientService<ClientDTO> {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public List<ClientDTO> list(ClientDTO i) throws DomainException {
        return null;
    }

    @Override
    public Optional<ClientDTO> findById(Long id) throws DomainException {
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> list() throws DomainException {
        return clientRepository
                .listAll()
                .stream()
                .map(CustomerMapper.entityToApi)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<ClientDTO> save(ClientDTO i) throws DomainException {
        DocumentType documentType = documentRepository.findByType(i.getDocumentId());

        return Stream.of(i)
                .map(CustomerMapper.apiToEntity)
                .peek(client -> client.setDocumentType(documentType))
                .map(client -> clientRepository.save(client))
                .map(CustomerMapper.entityToApi)
                .findFirst();
    }

    @Override
    @Transactional
    public Optional<ClientDTO> update(ClientDTO i) throws DomainException {
        Optional<Client> foundClient = clientRepository.findDocumentNumber(i.getDocument());
        validateNotFound(foundClient);
        return foundClient
                .stream()
                .map(client -> build(i, client))
                .map(client -> clientRepository.save(client))
                .map(CustomerMapper.entityToApi)
                .findFirst();
    }

    @Override
    @Transactional
    public Optional<ClientDTO> delete(String document) throws DomainException {
        Optional<Client> foundClient = clientRepository.findDocumentNumber(document);
        validateNotFound(foundClient);
        return foundClient
                .stream()
                .peek(client -> clientRepository.deleteLogic(client.getClientId()))
                .map(CustomerMapper.entityToApi)
                .findFirst();

    }

    @Override
    public Optional<ClientDTO> delete(Long id) throws DomainException {
        Optional<Client> foundClient = clientRepository.findById(id);
        validateNotFound(foundClient);
        return foundClient
                .stream()
                .peek(client -> clientRepository.delete(client))
                .map(CustomerMapper.entityToApi)
                .findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDTO> findByDocument(String document) throws DomainException {
        Optional<Client> client = clientRepository.findDocumentNumber(document);
        validateNotFound(client);
        return client
                .map(CustomerMapper.entityToApi);
    }

}
