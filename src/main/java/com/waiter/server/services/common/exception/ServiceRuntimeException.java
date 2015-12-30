package com.waiter.server.services.common.exception;

/**
 * Created by Admin on 12/23/2015.
 */
public class ServiceRuntimeException extends RuntimeException {

    public ServiceRuntimeException(final Throwable cause) {
        super(cause);
    }

    public ServiceRuntimeException(final String message) {
        super(message);
    }

    public ServiceRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
