package com.waiter.server.externalclients.common.http.exception;

public class ExternalClientRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -4285381500428801260L;

    /* Constructors */
    public ExternalClientRuntimeException(final Throwable throwable) {
        super(throwable);
    }

    public ExternalClientRuntimeException(final String message) {
        super(message);
    }

    public ExternalClientRuntimeException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
