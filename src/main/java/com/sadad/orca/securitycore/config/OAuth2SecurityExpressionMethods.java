package com.sadad.orca.securitycore.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

/**
 * @author Mahdad Aioby
 */
public class OAuth2SecurityExpressionMethods {
    private final Authentication authentication;

    public OAuth2SecurityExpressionMethods(Authentication authentication) {
        this.authentication = authentication;
    }

    public boolean hasScope(String scope) {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            return false;
        }

        Collection<String> tokenScopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("SCOPE_"))
                .map(auth -> auth.substring(6)) // Remove "SCOPE_" prefix
                .toList();

        return tokenScopes.contains(scope.trim());
    }
    public boolean hasAnyScope(String... scopes) {
        if (!(authentication instanceof JwtAuthenticationToken)) {
            return false;
        }

        Collection<String> tokenScopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("SCOPE_"))
                .map(auth -> auth.substring(6))
                .toList();

        for (String scope : scopes) {
            if (tokenScopes.contains(scope.trim())) {
                return true;
            }
        }
        return false;
    }
}
