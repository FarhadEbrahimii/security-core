package com.sadad.orca.securitycore.feign;

import feign.*;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
/**
 * @author Mahdad Aioby
 */
public class FeignClientConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
