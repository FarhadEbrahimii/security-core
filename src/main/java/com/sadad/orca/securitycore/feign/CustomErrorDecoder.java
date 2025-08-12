package com.sadad.orca.securitycore.feign;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;

/**
 * @author Mahdad Aioby
 */
public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        // Handle 401 specifically for token refresh
        if (response.status() == 401) {
            return new RetryableException(
                    response.status(),
                    "Unauthorized - possible token expiration",
                    response.request().httpMethod(),
                    3L,
                    response.request());
        }

        // For all other errors, use default decoder with proper body handling
        try {
            // Read body only once and convert to byte array
            byte[] bodyData = null;
            if (response.body() != null) {
                bodyData = Util.toByteArray(response.body().asInputStream());
            }

            Response modifiedResponse = Response.builder()
                    .status(response.status())
                    .reason(response.reason())
                    .headers(response.headers())
                    .request(response.request())
                    .body(bodyData)
                    .build();

            return defaultDecoder.decode(methodKey, modifiedResponse);
        } catch (IOException e) {
            return new FeignException.FeignClientException(
                    response.status(),
                    "Error reading response body: " + e.getMessage(),
                    response.request(),
                    null,
                    response.headers());
        }
    }
}