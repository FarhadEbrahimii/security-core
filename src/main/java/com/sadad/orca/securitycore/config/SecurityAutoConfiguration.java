package com.sadad.orca.securitycore.config;

import com.sadad.orca.securitycore.filter.ClientAwareJwtTokenFilter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

/**
 * @author Mahdad Aioby
 */
@AutoConfiguration
@ComponentScan(basePackages = {"com.sadad.orca"})
@Import({SecurityConfig.class, MethodSecurityConfig.class, ClientAwareJwtTokenFilter.class})
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class SecurityAutoConfiguration {
}
