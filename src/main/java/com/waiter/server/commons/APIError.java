package com.waiter.server.commons;

/**
 * Created by shahen on 11/3/14.
 */
public class APIError {

    private int code;
    private String message;
    private String description;

    public APIError(int code, String message) {
        this(code, message, null);
    }
    public APIError(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }


    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
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
                "code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
