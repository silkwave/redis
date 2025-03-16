package redis.nyc.redis.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j // Lombok을 사용하여 Logger 자동 생성
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // Save string to Redis
    public void saveString(String key, String value) {
        log.info("Saving key: {} with value: {}", key, value); // 로그 추가
        redisTemplate.opsForValue().set(key, value);
        log.info("Saved key: {} with value: {}", key, value); // 로그 추가
    }

    // Get string from Redis
    public String getString(String key) {
        log.info("Fetching value for key: {}", key); // 로그 추가
        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            log.info("Retrieved value for key: {}: {}", key, value); // 로그 추가
        } else {
            log.warn("No value found for key: {}", key); // 값이 없을 때 경고 로그 추가
        }
        return value;
    }

    // Delete key from Redis
    public void deleteKey(String key) {
        log.info("Deleting key: {}", key); // 로그 추가
        boolean deleted = redisTemplate.delete(key);
        if (deleted) {
            log.info("Deleted key: {}", key); // 로그 추가
        } else {
            log.warn("Failed to delete key: {}", key); // 삭제 실패시 경고 로그 추가
        }
    }

    public List<Map<String, String>> getAllKeysAndValues() {
        log.info("Fetching all keys and their values from Redis."); // 로그 추가
        Set<String> keys = redisTemplate.keys("*");
        List<Map<String, String>> resultList = new ArrayList<>();

        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                String value = redisTemplate.opsForValue().get(key);
                Map<String, String> entry = new HashMap<>();
                entry.put("key", key);
                entry.put("value", value);
                resultList.add(entry);
                log.info("Key: {} | Value: {}", key, value); // 로그 추가
            }
        } else {
            log.warn("No keys found in Redis."); // 키가 없을 때 경고 로그 추가
        }
        return resultList;
    }

}
