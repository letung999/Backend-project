package com.ppn.mock.backendmockppn.utils;

public class BuildCacheKey {
    public static String buildCacheKeyAllUsers(String prefixKey, int pageIndex, int pageSize) {
        StringBuilder stringBuilder = new StringBuilder(prefixKey);
        stringBuilder.append("-")
                .append(pageIndex)
                .append("-").append(pageSize);
        return stringBuilder.toString();
    }

}
