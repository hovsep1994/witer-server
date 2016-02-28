package com.waiter.server.api.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waiter.server.services.common.exception.ServiceError;

import java.util.Collections;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class ResponseEntity<TResponse> {

    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TResponse response;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiError> errors;


    public static <T> ResponseEntity<T> forResponse(T response) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.response = response;
        responseEntity.status = "success";
        return responseEntity;
    }

    public static ResponseEntity<Void> forSuccess() {
        return forResponse(null);
    }

    public static ResponseEntity<Void> forError(ServiceError error) {
        ResponseEntity<Void> responseEntity = new ResponseEntity<>();
        responseEntity.status = "error";
        ApiError apiError = new ApiError(error.getErrorCode(), error.getMessage(), error.getDescription());
        responseEntity.errors = Collections.singletonList(apiError);
        return responseEntity;
    }

    public String getStatus() {
        return status;
    }

    public TResponse getResponse() {
        return response;
    }

    public List<ApiError> getErrors() {
        return errors;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResponse(TResponse response) {
        this.response = response;
    }

    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }
}
