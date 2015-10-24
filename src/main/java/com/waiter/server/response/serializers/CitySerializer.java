package com.waiter.server.response.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.waiter.server.commons.entities.City;

import java.io.IOException;

/**
 * @author shahenpoghosyan
 */
public class CitySerializer extends JsonSerializer<City> {
    @Override
    public void serialize(City city, JsonGenerator jg, SerializerProvider sp)
            throws IOException, JsonProcessingException {
        jg.writeString(city.getName());
    }
}
