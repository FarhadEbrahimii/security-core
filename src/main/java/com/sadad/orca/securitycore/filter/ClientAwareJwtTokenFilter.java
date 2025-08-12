package com.sadad.orca.securitycore.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.sadad.orca.securitycore.config.OAuth2ClientProperties;
import com.sadad.orca.securitycore.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mahdad Aioby
 */
@Service
@RequiredArgsConstructor
public class ClientAwareJwtTokenFilter extends OncePerRequestFilter {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2ClientProperties properties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            try {
                if (authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                DecodedJWT jwt = JWT.decode(authHeader);
                Map claims = SecurityUtil.getClaims(authHeader,properties.getClients());
                // Create authentication object
                List<SimpleGrantedAuthority> authorities = jwt.getClaim("scopes").asList(String.class).stream()
                        .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                        .collect(Collectors.toList());
                // Create principal
                assert claims != null;
                OAuth2User principal = new DefaultOAuth2User(authorities, claims, "aud");

                // Create authentication token
                OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(
                        principal,
                        authorities,
                        clientRegistrationRepository.findByRegistrationId("localhost").getRegistrationId()
                );

                // Create authorized client with proper ClientRegistration
                ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("localhost")
                        .clientId(principal.getName())
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .tokenUri("http://api.bmi.ir/security")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) // or other method
                        .build();

                OAuth2AuthorizedClient authorizedClient = new OAuth2AuthorizedClient(
                        clientRegistration,
                        authentication.getName(),
                        new OAuth2AccessToken(
                                OAuth2AccessToken.TokenType.BEARER,
                                authHeader,
                                Instant.now(),
                                jwt.getExpiresAt().toInstant()
                        )
                );

                // Store in context
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Store authorized client in context
                OAuth2AuthorizedClientRepository authorizedClientRepository = new HttpSessionOAuth2AuthorizedClientRepository();
                authorizedClientRepository.saveAuthorizedClient(
                        authorizedClient,
                        authentication,
                        request,
                        response
                );

            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT: " + e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }





}