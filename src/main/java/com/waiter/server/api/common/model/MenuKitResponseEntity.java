package com.waiter.server.api.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waiter.server.services.common.exception.ServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author shahenpoghosyan
 */
public class MenuKitResponseEntity<TResponse> {

    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TResponse response;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiError> errors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Metadata metadata;


    public static <T> ResponseEntity<MenuKitResponseEntity<T>> success(T response) {
        MenuKitResponseEntity<T> responseEntity = new MenuKitResponseEntity<>();
        responseEntity.response = response;
        responseEntity.status = "success";


        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    public static <T> ResponseEntity<MenuKitResponseEntity<T>> success(T response, String url, Map<String, Object> params) {
        MenuKitResponseEntity<T> responseEntity = new MenuKitResponseEntity<>();
        responseEntity.response = response;
        responseEntity.status = "success";
        Metadata metadata = new Metadata();
        metadata.setNextPage(constructUrl(url, params));
        responseEntity.metadata = metadata;


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

    public Metadata getMetadata() {
        return metadata;
    }

    public MenuKitResponseEntity setMetadata(Metadata metadata) {
        this.metadata = metadata;
        return this;
    }

    private static String constructUrl(String url, Map<String, Object> queryString) {
        if(queryString == null || queryString.isEmpty())
            return url;
        return url + "?" + mapToQueryString(queryString);
    }

    private static String mapToQueryString(Map<String, Object> queryString) {
        StringBuilder sb = new StringBuilder();
        for(HashMap.Entry<String, Object> e : queryString.entrySet()){
            if (e.getValue() != null) {
                if (sb.length() > 0) {
                    sb.append('&');
                }
                try {
                    sb.append(URLEncoder.encode(e.getKey(), "UTF-8")).append('=').append(URLEncoder.encode(e.getValue().toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                    return null;
                }
            }
        }
        return sb.toString();
    }
}
