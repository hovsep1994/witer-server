package com.waiter.server.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waiter.server.commons.APIError;
import com.waiter.server.utils.ResponseList;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by shahen on 11/18/14.
 */
public class JsonResponseWriter<T> implements IResponseWriter<T>, Closeable {

    private static final Logger LOG = Logger.getLogger(JsonResponseWriter.class);
    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();

    static {
        DEFAULT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private Writer writer;
    private ObjectMapper mapper;

    public JsonResponseWriter(Writer writer) {
        this(writer, DEFAULT_MAPPER);
    }

    public JsonResponseWriter(Writer writer, ObjectMapper mapper) {
        this.writer = writer;
        this.mapper = mapper;
    }

    @Override
    public void writeResponse(T response) throws IOException {
        writer.write("{\"success\": true");
        if (response != null) {
            LOG.debug("response" + response);
            writer.write(", \"response\": " + mapper.writeValueAsString(response));
        }
        writer.write("}");
    }

    @Override
    public void writeError(APIError error) throws IOException {
        writer.write("{\"success\": false, \"error\": ");
        writer.write(mapper.writeValueAsString(error));
        writer.write("}");
    }

    @Override
    public void writeSuccess() throws IOException {
        writeResponse(null);
    }

    @Override
    public void writeListResponse(ResponseList<T> records) throws IOException {
        writer.write("{\"success\": true");
        int totalCount = 0;
        if (records != null) {
            totalCount = records.total();
        }
        if (records.isTotalValid()) {
            writer.write(", \"totalCount\":" + totalCount);
        }
        writer.write(", \"response\": [");
        if (records != null) {
            boolean first = true;
            for (T record : records) {
                if (!first) {
                    writer.write(",");
                    first = false;
                } else {
                    first = false;
                }
                writer.write(mapper.writeValueAsString(record));
            }
        }
        writer.write("]}");
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
