package com.sadad.orca.securitycore.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public class Response<T> {

    private ResponseError error;
    private T response;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> additionalProperties;

    @Override
    public int hashCode() {
        int result = getError() != null ? getError().hashCode() : 0;
        result = 31 * result + (getResponse() != null ? getResponse().hashCode() : 0);
        result = 31 * result + (getAdditionalProperties() != null ? getAdditionalProperties().hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response)) return false;

        Response<?> response1 = (Response<?>) o;

        if (getError() != null ? !getError().equals(response1.getError()) : response1.getError() != null) return false;
        if (getResponse() != null ? !getResponse().equals(response1.getResponse()) : response1.getResponse() != null)
            return false;
        return getAdditionalProperties() != null ? getAdditionalProperties().equals(response1.getAdditionalProperties()) : response1.getAdditionalProperties() == null;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @Override
    public String toString() {
        return "Response{" +
                ", error=" + error +
                ", response=" + response +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
