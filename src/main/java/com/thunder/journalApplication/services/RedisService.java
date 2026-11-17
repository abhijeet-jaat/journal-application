
package com.thunder.journalApplication.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);

            // Handle cache miss
            if (o == null) {
                return null;
            }

            return mapper.readValue(o.toString(), entityClass);

        } catch (Exception e) {
            log.error("Redis GET failed for key: {}", key, e);
            return null;
        }
    }

    public void set(String key, Object o, long ttl) {
        try {
            if (key == null || o == null) {
                log.warn("Redis SET skipped: key or value is null");
                return;
            }

            String jsonValue = mapper.writeValueAsString(o);

            redisTemplate.opsForValue().set(
                    key,
                    jsonValue,
                    ttl,
                    TimeUnit.MINUTES
            );

        } catch (Exception e) {
            log.error("Redis SET failed for key: {}", key, e);
        }
    }
}
