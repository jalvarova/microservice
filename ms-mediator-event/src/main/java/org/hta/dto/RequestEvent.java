package org.hta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestEvent {

    @NotNull(message = "Metadata no puede ser nulo")
    private Metadata metadata;

    private String jsonPayload;
}
