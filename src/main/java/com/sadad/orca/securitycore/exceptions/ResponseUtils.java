package com.sadad.orca.securitycore.exceptions;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import com.sadad.orca.securitycore.dto.response.Response;
import com.sadad.orca.securitycore.dto.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.sadad.orca.securitycore.exceptions.*;
import java.time.ZonedDateTime;
import java.util.List;


public abstract class ResponseUtils {

    public static Response createErrorResponse(ErrorCode errorCode, String message, String domain, List<ErrorData> errorData) {
        ResponseError responseError = new ResponseError();
        responseError.setNumber(errorCode.getHttpCode());
        responseError.setCode(errorCode.getCode());
        responseError.setMessage(message);
        responseError.setTimestamp(ZonedDateTime.now());
        responseError.setDomain(domain);
        if (CollectionUtils.isNotEmpty(errorData)) {
            responseError.setErrors(errorData);
        }
        Response response = new Response();
        response.setError(responseError);
        return response;
    }

    public static ResponseEntity<Object> createResponseEntity(ErrorCode errorCode, String message, String domain, List<ErrorData> errorData, HttpStatus status) {
        return new ResponseEntity<>(createErrorResponse(errorCode, message, domain, errorData), status);
    }

    public static ResponseEntity<Object> createResponseEntity(ErrorCode errorCode, String message, HttpStatus status) {
        return new ResponseEntity<>(createErrorResponse(errorCode, message, ErrorDomainDefinition.GLOBAL.getDomain(), null), status);
    }

    public static ResponseEntity<Object> createResponseEntity(ErrorCode errorCode, String message, List<ErrorData> errorData, HttpStatus status) {
        return new ResponseEntity<>(createErrorResponse(errorCode, message, ErrorDomainDefinition.GLOBAL.getDomain(), errorData), status);
    }

}
