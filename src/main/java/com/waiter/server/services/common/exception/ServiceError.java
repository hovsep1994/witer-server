package com.waiter.server.services.common.exception;

/**
 * Created by shahen on 11/3/14.
 */
public class ServiceError {

    private ErrorCode errorCode;
    private String message;
    private String description;

    public ServiceError(ErrorCode errorCode, String message) {
        this(errorCode, message, null);
    }

    public ServiceError(ErrorCode errorCode, String message, String description) {
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
        return "APIError{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
