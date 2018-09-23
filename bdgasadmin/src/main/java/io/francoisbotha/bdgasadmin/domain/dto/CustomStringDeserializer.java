package io.francoisbotha.bdgasadmin.domain.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

// Courtesy:
// https://stackoverflow.com/questions/39041496/how-to-enforce-accept-single-value-as-array-in-jacksons-deserialization-process
public class CustomStringDeserializer extends JsonDeserializer<List<String>> {

    @Override
    public List<String> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature. ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return mapper.readValue(p, List.class);
    }

}