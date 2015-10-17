package com.waiter.server.response;

import com.waiter.server.commons.APIError;
import com.waiter.server.utils.ResponseList;

import java.io.IOException;

/**
 * Created by shahen on 11/18/14.
 */
public interface IResponseWriter<T> {

    public void writeResponse(T response) throws IOException;
    public void writeError(APIError error) throws IOException;
    public void writeSuccess() throws IOException;
    public void writeListResponse(ResponseList<T> records) throws IOException;
}
