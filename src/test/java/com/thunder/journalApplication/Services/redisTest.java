package com.thunder.journalApplication.Services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class redisTest {
@Autowired
    private RedisTemplate redisTemplate;
    @Disabled
    @Test
    public void redisTest() {
        redisTemplate.opsForValue().set("key", "value");
        Object value = redisTemplate.opsForValue().get("key");
        int a = 1;
    }
}
