package org.hta.thirdparty.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestEvent implements Serializable {

    @NotNull(message = "Metadata no puede ser nulo")
    private Metadata metadata;

    private String jsonPayload;
}
