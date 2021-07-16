package org.hta.mappers;


import org.hta.domain.entity.Permission;
import org.hta.domain.entity.Role;
import org.hta.domain.entity.User;
import org.hta.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@FunctionalInterface
public interface UserMapper {

    void example();


    Function<User, UserDTO> entityToApi = (User entity) -> {
        List<Role> roles = entity.getUserRoles();

        List<String> roleNames = roles.stream().map(Role::getRoleName).collect(toList());
        List<String> permissions =
                roles
                        .stream()
                        .map(Role::getRolePermission)
                        .flatMap(Collection::parallelStream)
                        .map(Permission::getPermissionName)
                        .collect(toList());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return UserDTO
                .builder()
                .username(entity.getUsername())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .permission(permissions)
                .roles(roleNames)
                .build();
    };

    Function<UserDTO, User> apiToEntity = (UserDTO api) -> {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return User
                .builder()
                .username(api.getUsername())
                .name(api.getName())
                .lastName(api.getLastName())
                .password(encoder.encode(api.getPassword()))
                .enabled(Boolean.TRUE)
                .isBlocked(Boolean.FALSE)
                .build();
    };
    Function<User, User> apiToEntityUpdate = (User api) -> User
            .builder()
            .name(api.getName())
            .lastName(api.getLastName())
            .build();
}
