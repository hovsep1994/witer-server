package com.waiter.server.api.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waiter.server.services.common.exception.ServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class MenuKitResponseEntity<TResponse> {

    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TResponse response;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiError> errors;


    public static <T> ResponseEntity<MenuKitResponseEntity<T>> success(T response) {
        MenuKitResponseEntity<T> responseEntity = new MenuKitResponseEntity<>();
        responseEntity.response = response;
        responseEntity.status = "success";


        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    public static ResponseEntity<MenuKitResponseEntity<Void>> success() {
        return success(null);
    }

    @Deprecated
    public static <T> MenuKitResponseEntity<T> success2(T response) {
        MenuKitResponseEntity<T> responseEntity = new MenuKitResponseEntity<>();
        responseEntity.response = response;
        responseEntity.status = "success";

        return responseEntity;
    }

    @Deprecated
    public static <T> MenuKitResponseEntity<T> success2() {
        return success2(null);
    }

    public static ResponseEntity<MenuKitResponseEntity<Void>> error(ServiceError error, HttpStatus httpStatus) {
        MenuKitResponseEntity<Void> responseEntity = new MenuKitResponseEntity<>();
        responseEntity.status = "error";
        ApiError apiError = new ApiError(error.getErrorCode(), error.getMessage(), error.getDescription());
        responseEntity.errors = Collections.singletonList(apiError);

        return new ResponseEntity<>(responseEntity, httpStatus);
    }

    public static ResponseEntity<MenuKitResponseEntity<Void>> error(ServiceError error) {
        return error(error, HttpStatus.BAD_REQUEST);
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
