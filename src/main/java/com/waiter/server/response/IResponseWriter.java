package com.waiter.server.response;

import com.waiter.server.commons.APIError;
import com.waiter.server.utils.ResponseList;

import java.io.IOException;
import java.util.List;

/**
 * Created by shahen on 11/18/14.
 */
public interface IResponseWriter<T> {

    void writeResponse(T response) throws IOException;
    void writeError(APIError error) throws IOException;
    void writeSuccess() throws IOException;
    void writeListResponse(ResponseList<T> records) throws IOException;
    void writeListResponse(List<T> records) throws IOException;
}
