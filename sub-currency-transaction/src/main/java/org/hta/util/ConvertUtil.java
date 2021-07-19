package org.hta.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class ConvertUtil {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static <T> T fileToObject(String path, Class<T> objectResponse) throws IOException {

        return objectMapper.readValue(ConvertUtil.class.getClassLoader().getResourceAsStream(path), objectResponse);
    }

    public static String jsonToString(final Object obj) {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T stringToObject(String json, Class<T> objectResponse) {
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        try {
            return objectMapper.readValue(json, objectResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T stringToObjectTransport(String json, Class<T> objectResponse) throws IOException {
        return objectMapper.readValue(json, objectResponse);
    }

    public <T> T decodeObjectTransport(String raw, Class<T> oTransport) throws JsonProcessingException {
        return objectMapper.readValue(raw, oTransport);
    }
}
