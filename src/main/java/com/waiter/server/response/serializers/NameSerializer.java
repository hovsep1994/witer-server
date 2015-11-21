package com.waiter.server.response.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.waiter.server.commons.entities.Name;

import java.io.IOException;

/**
 * @author shahenpoghosyan
 */
public class NameSerializer extends JsonSerializer<Name> {

    @Override
    public void serialize(Name name, JsonGenerator jg, SerializerProvider sp) throws IOException {
        if(name == null) {
            jg.writeNull();
        } else {
            jg.writeString(name.getName());
        }
    }
}
