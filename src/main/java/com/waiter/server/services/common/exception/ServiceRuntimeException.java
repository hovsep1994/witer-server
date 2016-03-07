package com.waiter.server.services.common.exception;

/**
 * Created by Admin on 12/23/2015.
 */
public class ServiceRuntimeException extends RuntimeException {
    private ServiceError error;

    public ServiceRuntimeException(ServiceError error) {
        super(error.getMessage());
        this.error = error;
    }

    public ServiceRuntimeException(ErrorCode errorCode, String message) {
        super(message);
        this.error = new ServiceError(errorCode, message);
    }

    public ServiceError getError() {
        return error;
    }

    public void setError(ServiceError error) {
        this.error = error;
    }
}
