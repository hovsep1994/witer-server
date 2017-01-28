package com.waiter.server.api.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waiter.server.services.common.exception.ErrorCode;

/**
 * Created by hovsep on 2/28/16.
 */
public class ApiError {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorCode errorCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    public ApiError(ErrorCode errorCode, String message) {
        this(errorCode, message, null);
    }

    public ApiError(ErrorCode errorCode, String message, String description) {
        this.errorCode = errorCode;
        this.message = message;
        this.description = description;
    }


    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
