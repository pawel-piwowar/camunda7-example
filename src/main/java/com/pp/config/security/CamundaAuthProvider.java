package com.pp.config.security;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import org.camunda.bpm.engine.ProcessEngine;
        import org.camunda.bpm.engine.rest.security.auth.AuthenticationProvider;
        import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class CamundaAuthProvider implements AuthenticationProvider {
    public CamundaAuthProvider() {
    }

    public AuthenticationResult extractAuthenticatedUser(HttpServletRequest request, ProcessEngine engine) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return AuthenticationResult.unsuccessful();
        }

        String name = principal.getName();
        AuthenticationResult authenticationResult = new AuthenticationResult(name, true);
        List<String> groups = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        authenticationResult.setGroups(groups);
        return name != null && !name.isEmpty() ? authenticationResult : AuthenticationResult.unsuccessful();
    }

    public void augmentResponseByAuthenticationChallenge(HttpServletResponse response, ProcessEngine engine) {
    }
}