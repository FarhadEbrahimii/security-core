/*
 * Copyright 2017 (c) sadad.co.ir
 */

package com.sadad.orca.securitycore.exceptions;



public enum ErrorDefinition implements ErrorCode {

    CONSTRAINT_VIOLATION_EXCEPTION(10001, "CONSTRAINT_VIOLATION", 400, true, "constraint violation exception occurred"),
    METHOD_NOT_SUPPORTED_EXCEPTION(10002, "METHOD_NOT_SUPPORTED_EXCEPTION", 400, true, "method is not supported for request"),
    MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION(10003, "MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION", 400, true, "media type is not supported for request"),
    MESSAGE_NOT_READABLE_EXCEPTION(10004, "MESSAGE_NOT_READABLE_EXCEPTION", 400, true, "message not readable exception occurred"),
    MISS_PATH_VARIABLE_EXCEPTION(10005, "MISS_PATH_VARIABLE_EXCEPTION", 400, true, "path variable is missing"),
    BIND_EXCEPTION(10006, "BIND_EXCEPTION", 400, true, "parameter bind exception occurred"),
    AUTHENTICATION_EXCEPTION(10007, "AUTHENTICATION_EXCEPTION", 401, true, "Authentication Exception occurred"),
    ACCESS_FORBIDEN_EXCEPTION(10008, "ACCESS_FORBIDEN_EXCEPTION", 403, true, "access forbid exception"),
    HANDLER_NOT_FOUND_EXCEPTION(10009, "HANDLER_NOT_FOUND_EXCEPTION", 404, true, "handler not found"),
    EXPIRED_TOKEN_EXCEPTION(100010, "EXPIRED_TOKEN_EXCEPTION", 401, true, "Expired Token Exception occurred"),
    NOT_FOUND_EXCEPTION(100011, "NOT_FOUND_EXCEPTION", 404, true, "not found"),
    BAD_REQUEST(10012, "BAD_REQUEST", 400, true, "bad request exception occurred"),
    NOT_SUPPORTED_ACCESS_TOKEN_ALGORITHM(10013, "NOT_SUPPORTED_ACCESS_TOKEN_ALGORITHM", 401, true, "not supported access token algorithm"),
    ACCESS_TOKEN_DECODE_EXCEPTION(10014, "ACCESS_TOKEN_DECODE_EXCEPTION", 401, true, "access token decode exception"),
    INVALID_TOKEN_EXCEPTION(10015, "INVALID_TOKEN_EXCEPTION", 401, true, "invalid access token"),
    CLIENT_ID_NOT_RECOGNIZED_EXCEPTION(10016, "CLIENT_ID_NOT_RECOGNIZED_EXCEPTION", 401, true, "client-id not recognized"),
    SIGNING_KEY_NOT_FOUND_EXCEPTION(10017, "SIGNING_KEY_NOT_FOUND_EXCEPTION", 401, true, "signing-key not found exception"),
    INTERNAL_SERVER_ERROR_EXCEPTION(100018, "INTERNAL_SERVER_ERROR", 500, true, "internal server error occurred"),
    ILLEGAL_ARGUMENT_OR_SIGNATURE(10019,"ILLEGAL_ARGUMENT_OR_SIGNATURE" , 401, true, "illegal argument or signature"),
    JWT_VERIFICATION_EXCEPTION(10020,"JWT_VERIFICATION_EXCEPTION",401,true, "jwt verification exception");

    private final int number;
    private final String code;
    private final int httpCode;
    private final boolean hasBody;
    private final String message;

    ErrorDefinition(int number, String code, int httpCode, boolean hasBody, String message) {
        this.number = number;
        this.code = code;
        this.httpCode = httpCode;
        this.hasBody = hasBody;
        this.message = message;
    }

    public int getNumber() {
        return number;
    }

    public String getCode() {
        return code;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public boolean isHasBody() {
        return hasBody;
    }

    public String getMessage() {
        return message;
    }

}
