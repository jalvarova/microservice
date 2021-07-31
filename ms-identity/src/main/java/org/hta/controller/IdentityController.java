package org.hta.controller;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;
import lombok.extern.slf4j.Slf4j;
import org.hta.aspect.TraceSpan;
import org.hta.domain.entity.Permission;
import org.hta.domain.entity.Role;
import org.hta.domain.entity.User;
import org.hta.dto.jwt.JwtRequest;
import org.hta.dto.jwt.JwtResponse;
import org.hta.service.JwtUserDetailsService;
import org.hta.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Validated
@RestController
public class IdentityController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/authentication")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid JwtRequest jwtRequest) throws Exception {

        Tracer tracer = GlobalTracer.get();
        Tracer.SpanBuilder spanBuilder = tracer.buildSpan("LoginCustom")
                .withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_SERVER);

        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        Span span2 = spanBuilder.start();
        Tags.COMPONENT.set(span2, "IdentityController");
        span2.setTag("users", "get-user");
        User user = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        span2.finish();

        List<Role> roles = user.getUserRoles();
        List<String> roleNames = roles.stream().map(Role::getRoleName).collect(toList());
        List<String> permissions =
                roles
                        .stream()
                        .map(Role::getRolePermission)
                        .flatMap(Collection::parallelStream)
                        .map(Permission::getPermissionName)
                        .collect(toList());

        final JwtResponse jwtResponse = JwtResponse
                .builder()
                .username(user.getUsername())
                .documentNumber(user.getDocumentNumber())
                .nameComplete(user.getName().concat(" ").concat(user.getLastName()))
                .permission(permissions)
                .roles(roleNames)
                .tokenType("Bearer")
                .expires_in(3600)
                .build();

        log.info("Response Token " + jwtResponse);
        final String token = jwtTokenUtil.generateToken(jwtResponse);

        return ResponseEntity.ok(jwtResponse.jwt(token));

    }

    @TraceSpan(key = "auth")
    public void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}
