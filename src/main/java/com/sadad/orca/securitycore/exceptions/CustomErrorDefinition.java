package com.sadad.orca.securitycore.exceptions;


public enum CustomErrorDefinition implements ErrorCode {

    ERROR_READING_FILE(10001, "ERROR_READING_FILE", 500, true, "can not read file"),;

    private final int number;
    private final String code;
    private final int httpCode;
    private final boolean hasBody;
    private final String message;

    CustomErrorDefinition(int number, String code, int httpCode, boolean hasBody, String message) {
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
