package com.waiter.server.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.waiter.server.commons.APIError;
import com.waiter.server.commons.entities.City;
import com.waiter.server.response.serializers.CitySerializer;
import com.waiter.server.utils.ResponseList;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by shahen on 11/18/14.
 */
public class JsonResponseWriter<T> implements IResponseWriter<T>, Closeable {

    private static final Logger LOG = Logger.getLogger(JsonResponseWriter.class);
    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();

    static {
        SimpleModule simpleModule = new SimpleModule("MyModule", new Version(1, 0, 0, null));
        simpleModule.addSerializer(City.class, new CitySerializer());
        DEFAULT_MAPPER.registerModule(simpleModule);
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
        if (records != null) {
            if (records.isTotalValid()) {
                writer.write(", \"totalCount\":" + records.total());
            }
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
    public void writeListResponse(List<T> records) throws IOException {
        writer.write("{\"success\": true");
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
