package org.hta.controller;

import org.hta.dto.UserDTO;
import org.hta.dto.jwt.JwtResponse;
import org.hta.exceptions.DomainException;
import org.hta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/ms-identity/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUsers(@RequestBody @Valid UserDTO request) throws DomainException {
        return ResponseEntity.ok(userService.save(request));
    }

    @PutMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUsers(@RequestBody @Valid UserDTO request) throws DomainException {
        return ResponseEntity.ok(userService.update(request));
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers() throws DomainException {
        return ResponseEntity.ok(userService.list());
    }

    @DeleteMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUsers(@PathVariable(name = "username") String username) throws DomainException {
        return ResponseEntity.ok(userService.delete(username));
    }

    @GetMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersByUserName(@PathVariable(name = "username") String username) throws DomainException {
        return ResponseEntity.ok(userService.findByUserName(username));
    }

    @GetMapping(value = "/users/{documentNumber}/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersByDocumentNumber(@PathVariable(name = "documentNumber") String documentNumber) throws DomainException {
        return ResponseEntity.ok(userService.findByDocumentNumber(documentNumber));
    }
}
