package com.ppn.mock.backendmockppn.utils;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GetAllKeyRedis {
    public List<String> getAllKeyRedis() {
        RedisTemplate redisTemplate = new RedisTemplate();
        Set<String> redisKeys = redisTemplate.keys("allUsers*");
        List<String> keysList = new ArrayList<>();
        Iterator<String> it = redisKeys.iterator();
        while (it.hasNext()) {
            String data = it.next();
            keysList.add(data);
        }
        return keysList;
    }
}
