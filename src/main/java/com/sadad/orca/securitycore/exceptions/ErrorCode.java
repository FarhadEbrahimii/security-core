package com.sadad.orca.securitycore.exceptions;


public interface ErrorCode {

    int getNumber();

    String getCode();

    int getHttpCode();

    boolean isHasBody();

    String getMessage();

    @Deprecated
    default void throwException() {
        throw new SadadException(this);
    }

    default SadadException exception() {
        return new SadadException(this);
    }

    @Deprecated
    default void throwException(String message) {
        throw new SadadException(message, this);
    }

    default SadadException exception(String message) {
        return new SadadException(message, this);
    }

    @Deprecated
    default void throwException(ErrorCode errorCode, ErrorDomainDefinition errorDomain) {
        throw new SadadException(errorCode, errorDomain);
    }

    @Deprecated
    default void throwException(ErrorDomainDefinition errorDomain) {
        throw new SadadException(this, errorDomain);
    }

    default SadadException exception(ErrorDomain errorDomain) {
        return new SadadException(this, errorDomain);
    }

    @Deprecated
    default void throwException(ErrorCode errorCode) {
        throw new SadadException(errorCode);
    }

    @Deprecated
    default void throwException(String message, Throwable cause, ErrorCode errorCode) {
        throw new SadadException(message, cause, errorCode);
    }

    @Deprecated
    default void throwException(String message, Throwable cause) {
        throw new SadadException(message, cause, this);
    }

    default SadadException exception(String message, Throwable cause) {
        return new SadadException(message, cause, this);
    }

    @Deprecated
    default void throwException(String message, Throwable cause, ErrorCode errorCode, ErrorDomainDefinition errorDomain) {
        throw new SadadException(message, cause, errorCode, errorDomain);
    }

    @Deprecated
    default void throwException(String message, Throwable cause, ErrorDomainDefinition errorDomain) {
        throw new SadadException(message, cause, this, errorDomain);
    }

    default SadadException exception(String message, Throwable cause, ErrorDomain errorDomain) {
        return new SadadException(message, cause, this, errorDomain);
    }

    @Deprecated
    default void throwException(Throwable cause, ErrorCode errorCode, ErrorDomainDefinition errorDomain) {
        throw new SadadException(cause, errorCode, errorDomain);
    }

    @Deprecated
    default void throwException(Throwable cause, ErrorDomainDefinition errorDomain) {
        throw new SadadException(cause, this, errorDomain);
    }

    default SadadException exception(Throwable cause, ErrorDomain errorDomain) {
        return new SadadException(cause, this, errorDomain);
    }
}
