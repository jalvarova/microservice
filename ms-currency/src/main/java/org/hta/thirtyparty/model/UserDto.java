package org.hta.thirtyparty.model;

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
public class UserDto {

    @NotEmpty(message = "username It can not be null")
    @NotNull(message = "username It can not be null")
    private String username;

    @NotEmpty(message = "name It can not be null")
    @NotNull(message = "name It can not be null")
    private String name;

    @NotEmpty(message = "lastName It can not be null")
    @NotNull(message = "lastName It can not be null")
    private String lastName;

    @NotNull(message = "roles It can not be null")
    private List<String> roles;
}
