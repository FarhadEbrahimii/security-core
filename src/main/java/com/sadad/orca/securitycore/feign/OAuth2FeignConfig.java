package com.sadad.orca.securitycore.feign;

import com.sadad.orca.securitycore.config.SadadProperties;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mahdad Aioby
 */
@Configuration
public class OAuth2FeignConfig {

    @Autowired
    private SadadProperties sadadProperties;

    @Bean
    public RequestInterceptor oAuth2AccessTokenInterceptor(
            OAuth2AuthorizedClientManager authorizedClientManager,ClientRegistration registration) {
        if(sadadProperties.getSecurity().getFeign().isEnabled())
            return new OAuth2AccessTokenInterceptor(authorizedClientManager);
        else {
            return new CustomOAuth2AccessTokenInterceptor(authorizedClientManager,registration);
        }
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository,
                        authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        authorizedClientManager.setContextAttributesMapper(authorizeRequest -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put(OAuth2AuthorizationContext.REQUEST_SCOPE_ATTRIBUTE_NAME, sadadProperties.getSecurity().getClientAuthorization().getScopes());
            return attributes;
        });
        return authorizedClientManager;
    }

    @Bean
    public ClientRegistration clientRegistration() {
        return ClientRegistration.withRegistrationId("localhost")
                .tokenUri(sadadProperties.getSecurity().getClientAuthorization().getAccessTokenUri())
                .clientId(sadadProperties.getSecurity().getClientAuthorization().getClientId())
                .clientSecret(sadadProperties.getSecurity().getClientAuthorization().getClientSecret())
                .scope(sadadProperties.getSecurity().getClientAuthorization().getScopes())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(ClientRegistration clientRegistration) {
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
}
