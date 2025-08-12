package com.sadad.orca.securitycore.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;


/**
 * @author Mahdad Aioby
 */

@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    private final OAuth2ClientProperties properties;

    public MethodSecurityConfig(OAuth2ClientProperties properties) {
        this.properties = properties;
    }


    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new CustomMethodSecurityExpressionHandler(properties);
    }
}