package com.sadad.orca.securitycore.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mahdad Aioby
 */
@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "security.oauth2")
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class OAuth2ClientProperties {
    private List<ClientConfig> clients = new ArrayList<>();
    private Map<String, String> scopes = new HashMap<>();

    @Data
    public static class ClientConfig {
        private String clientId;
        private String clientSecret;
    }

}
