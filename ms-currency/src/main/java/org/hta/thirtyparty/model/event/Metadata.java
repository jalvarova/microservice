package org.hta.thirtyparty.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Metadata {

    @NotNull(message = "origin no puede ser nulo")
    @NotEmpty(message = "origin no puede ser nulo")
    private String origin;

    @NotNull(message = "destination no puede ser nulo")
    @NotEmpty(message = "origin no puede ser nulo")
    private String destination;

    @NotNull(message = "app no puede ser nulo")
    @NotEmpty(message = "origin no puede ser nulo")
    private String app;

    @NotNull(message = "domain no puede ser nulo")
    @NotEmpty(message = "origin no puede ser nulo")
    private String domain;

    @NotNull(message = "broker no puede ser nulo")
    @NotEmpty(message = "origin no puede ser nulo")
    private BROKER broker;
}
