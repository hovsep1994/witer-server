package com.waiter.server.commons;

/**
 * Created by shahen on 11/19/14.
 */
public class APIException extends Exception {

    private APIError error;
    private int httpStatus;

    public APIException(String message) {
        super(message);
    }
    public APIException(int httpStatus, APIError error) {
        super(error.getMessage());
        this.httpStatus = httpStatus;
        this.error = error;
    }

    public APIError getError() {
        return error;
    }

    public void setError(APIError error) {
        this.error = error;
    }
}
