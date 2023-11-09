package com.ppn.mock.backendmockppn.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

public class MapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseStringJsonToClass(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseJsonArray(String json, Class<T[]> arrayClass) {
        T[] objects = null;
        try {
            objects = objectMapper.readValue(json, arrayClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList(objects);
    }

    public static String writeObjectToJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
