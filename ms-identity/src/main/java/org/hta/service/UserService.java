package org.hta.service;

import lombok.extern.slf4j.Slf4j;
import org.hta.aspect.LogTime;
import org.hta.domain.RoleRepository;
import org.hta.domain.UserRepository;
import org.hta.domain.entity.Role;
import org.hta.dto.UserDTO;
import org.hta.exceptions.DomainException;
import org.hta.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements IUserService<UserDTO> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @LogTime
    @Override
    public List<UserDTO> list(UserDTO i) throws DomainException {
        return null;
    }

    @LogTime
    @Override
    public Optional<UserDTO> findById(Long id) throws DomainException {
        return userRepository
                .findById(id)
                .stream()
                .map(UserMapper.entityToApi)
                .findFirst();
    }

    @LogTime
    @Override
    @Cacheable(value = "users", keyGenerator = "userKeyGenerator")
    @Transactional(readOnly = true)
    public List<UserDTO> list() throws DomainException {
        return userRepository
                .findUsers()
                .stream()
                .map(UserMapper.entityToApi)
                .collect(Collectors.toUnmodifiableList());
    }

    @LogTime
    @Override
    @Cacheable(value = "users", key = "#user.username")
    @Transactional
    public Optional<UserDTO> save(UserDTO user) throws DomainException {
        List<Role> roles = user.getRoles()
                .stream()
                .map(roleRepository::findByRoleName)
                .collect(Collectors.toList());

        return Optional.of(user)
                .stream()
                .map(UserMapper.apiToEntity)
                .peek(user1 -> user1.setUserRoles(roles))
                .map(userRepository::save)
                .map(UserMapper.entityToApi)
                .findAny()
                .or(() -> Optional.of(new UserDTO()));
    }

    @LogTime
    @Override
    @CachePut(value = "users", key = "#user.username")
    @Transactional
    public Optional<UserDTO> update(UserDTO user) throws DomainException {
        return userRepository
                .findByUsername(user.getUsername())
                .map(userFound -> UserMapper.apiToEntityUpdate.apply(user, userFound))
                .map(userRepository::save)
                .map(UserMapper.entityToApi)
                .or(() -> Optional.of(new UserDTO()));
    }

    @LogTime
    @Override
    @CacheEvict(value = "users", allEntries = true, keyGenerator = "userKeyGenerator")
    @Transactional
    public Optional<UserDTO> delete(String userName) throws DomainException {
        return userRepository
                .findByUsername(userName)
                .stream()
                .peek(user -> userRepository.delete(user))
                .map(UserMapper.entityToApi)
                .findAny()
                .or(() -> Optional.of(new UserDTO()));
    }

    @LogTime
    @Override
    public Optional<UserDTO> delete(Long id) throws DomainException {
        return userRepository
                .findById(id)
                .stream()
                .map(UserMapper.entityToApi)
                .findFirst()
                .or(() -> Optional.of(new UserDTO()));
    }

    @LogTime
    @Transactional(readOnly = true)
    @Override
    @Cacheable(value = "users", keyGenerator = "userKeyGenerator")
    public Optional<UserDTO> findByUserName(String keyUserName) throws DomainException {
        return userRepository
                .findByUsername(keyUserName)
                .stream()
                .map(UserMapper.entityToApi)
                .findFirst()
                .or(() -> Optional.of(new UserDTO()));
    }

    @LogTime
    @Transactional(readOnly = true)
    @Override
    public Optional<UserDTO> findByDocumentNumber(String documentNumber) throws DomainException {
        return userRepository
                .findByDocumentNumber(documentNumber)
                .stream()
                .map(UserMapper.entityToApi)
                .findFirst()
                .or(() -> Optional.of(new UserDTO()));
    }
}
