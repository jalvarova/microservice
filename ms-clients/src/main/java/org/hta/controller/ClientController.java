package org.hta.controller;

import org.hta.dto.ClientDTO;
import org.hta.exceptions.DomainException;
import org.hta.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Secured("ADMIN")
@RestController
@RequestMapping("/ms-clients/api/v1")
public class ClientController {

    @Autowired

    private IClientService<ClientDTO> clientService;

    @GetMapping("customers")
    public List<ClientDTO> findAll() throws DomainException {
        return clientService.list();
    }

    @PostMapping("customers")
    public ResponseEntity<Optional<ClientDTO>> save(@NotNull @RequestBody ClientDTO request) throws DomainException {
        return new ResponseEntity<>(clientService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("customers")
    public Optional<ClientDTO> update(@NotNull @RequestBody ClientDTO request) throws DomainException {
        return clientService.update(request);
    }

    @GetMapping("customers/{document}")
    public Optional<ClientDTO> findById(@PathVariable("document") String document) throws DomainException {
        return clientService.findByDocument(document);
    }

    @DeleteMapping("customers/{document}")
    public Optional<ClientDTO> delete(@PathVariable("document") String document) throws DomainException {
        return clientService.delete(document);
    }

    @DeleteMapping("customers/{id}/physical")
    public Optional<ClientDTO> delete(@NotNull @PathVariable("id") Long id) throws DomainException {
        return clientService.delete(id);
    }
}
