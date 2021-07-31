package org.hta.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    @NotEmpty(message = "username It can not be null")
    @NotNull(message = "username It can not be null")
    private String username;

    @NotEmpty(message = "name It can not be null")
    @NotNull(message = "name It can not be null")
    private String name;

    @NotEmpty(message = "lastName It can not be null")
    @NotNull(message = "lastName It can not be null")
    private String lastName;

    @NotEmpty(message = "lastName It can not be null")
    @NotNull(message = "lastName It can not be null")
    private String password;

    @NotEmpty(message = "documentNumber It can not be null")
    @NotNull(message = "documentNumber It can not be null")
    private String documentNumber;

    @NotNull(message = "roles It can not be null")
    private List<String> roles;

    @NotNull(message = "permission It can not be null")
    private List<String> permission;
}
