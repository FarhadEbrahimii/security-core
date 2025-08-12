package com.sadad.orca.securitycore.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

/**
 * @author Mahdad Aioby
 */
public class CustomOAuth2AccessTokenInterceptor implements RequestInterceptor {
    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private final ClientRegistration clientRegistration;

    public CustomOAuth2AccessTokenInterceptor(
            OAuth2AuthorizedClientManager authorizedClientManager,
            ClientRegistration clientRegistration) {
        this.authorizedClientManager = authorizedClientManager;
        this.clientRegistration = clientRegistration;
    }

    @Override
    public void apply(RequestTemplate template) {
        if(!hasAuthorizationHeader(template)) {
           OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                   .withClientRegistrationId(clientRegistration.getRegistrationId())
                   .principal("feign-client")
                   .attributes(attrs -> {
                       attrs.put(OAuth2AuthorizationContext.REQUEST_SCOPE_ATTRIBUTE_NAME,
                               clientRegistration.getScopes());
                   })
                   .build();

           OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

           if (authorizedClient != null && authorizedClient.getAccessToken() != null) {
               template.header("Authorization", "Bearer " + authorizedClient.getAccessToken().getTokenValue());
           }
       }
    }

    public boolean hasAuthorizationHeader(RequestTemplate template) {
        return template.headers().containsKey("Authorization");
    }
}
