package com.ppn.mock.backendmockppn.utils;


import com.google.common.io.Resources;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GetMockData {
    public static <T> List<T> getMockData(String fileName, Class<T[]> objects) throws IOException {
        InputStream inputStream = Resources.getResource(fileName).openStream();
        String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        T[] result = new Gson().fromJson(json, objects);
        return Arrays.asList(result);
    }
}
