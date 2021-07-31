package org.hta.dto.jwt;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class JwtResponse implements Serializable {

    private String username;
    private String nameComplete;
    private String documentNumber;
    private List<String> roles;
    private List<String> permission;
    private int expires_in;
    private String token;
    private String refresh;
    private String tokenType;

    public JwtResponse jwt(String jwtToken) {
        this.token = jwtToken;
        this.refresh = jwtToken;
        return this;
    }
}
