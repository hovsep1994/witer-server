package com.waiter.server.services.common.exception;

/**
 * Created by shahen on 11/19/14.
 */
public class ServiceException extends Exception {

    private ServiceError error;

    public ServiceException(ServiceError error) {
        super(error.getMessage());
        this.error = error;
    }

    public ServiceException(ErrorCode errorCode, String message) {
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
