/*
 * Copyright 2017 (c) sadad.co.ir
 */

package com.sadad.orca.securitycore.exceptions;

public enum ErrorDomainDefinition implements ErrorDomain {

    GLOBAL(100001, "GLOBAL"),
    NOTIFICATION_SUB_SERVICE(100002, "NOTIFICATION_DOMAIN"),
    DATABASE_SUB_SERVICE(100003, "DATABASE_DOMAIN"),
    EXTERNAL_SERVICE(100004, "EXTERNAL_SERVICE_DOMAIN");

    private final int number;
    private final String domain;

    ErrorDomainDefinition(int number, String domain) {
        this.number = number;
        this.domain = domain;
    }

    public int getNumber() {
        return number;
    }

    public String getDomain() {
        return domain;
    }
}
