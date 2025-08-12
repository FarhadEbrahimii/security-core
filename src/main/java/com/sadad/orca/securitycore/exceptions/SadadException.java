/*
 * Copyright 2017 (c) sadad.co.ir
 */

package com.sadad.orca.securitycore.exceptions;

import java.util.List;


public class SadadException extends RuntimeException {

    private ErrorCode errorCode;
    private ErrorDomain errorDomain;
    private String errorMessage;
    private List<ErrorData> errorData;

    public SadadException(ErrorCode errorCode, ErrorDomain errorDomain) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorDomain = errorDomain;
    }

    public SadadException(ErrorCode errorCode, List<ErrorData> errorData) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorData = errorData;
        this.errorDomain = ErrorDomainDefinition.GLOBAL;
    }

    public SadadException(ErrorCode errorCode, ErrorDomain errorDomain, List<ErrorData> errorData) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorData = errorData;
        this.errorDomain = errorDomain;
    }

    public SadadException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorDomain = ErrorDomainDefinition.GLOBAL;
    }

    public SadadException(String message, ErrorCode errorCode, ErrorDomain errorDomain) {
        super(errorCode.getMessage());
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.errorDomain = errorDomain;
    }

    public SadadException(String message, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.errorDomain = ErrorDomainDefinition.GLOBAL;
    }

    public SadadException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
        this.errorDomain = ErrorDomainDefinition.GLOBAL;
    }

    public SadadException(Throwable cause, ErrorCode errorCode, ErrorDomain errorDomain) {
        super(errorCode.getMessage(),cause);
        this.errorCode = errorCode;
        this.errorDomain = errorDomain;
    }

    public SadadException(String message, Throwable cause, ErrorCode errorCode) {
        super(errorCode.getMessage(), cause);
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.errorDomain = ErrorDomainDefinition.GLOBAL;
    }

    public SadadException(String message, Throwable cause, ErrorCode errorCode, ErrorDomain errorDomain) {
        super(errorCode.getMessage(), cause);
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.errorDomain = errorDomain;
    }

    public List<ErrorData> getErrorData() {
        return errorData;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ErrorDomain getErrorDomain() {
        return errorDomain;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
