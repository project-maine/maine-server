package net.dengzixu.maine.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public String getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void getAndDeleteKey(String key) {
        redisTemplate.opsForValue().getAndDelete(key);
    }

    public void setKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setKey(String key, String value, long ttl) {
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
    }
}
