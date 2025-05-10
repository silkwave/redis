package redis.nyc.redis.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 문자열 저장
    public void saveString(String key, String value) {
        log.info("\n\n\n");        
        log.info("📝 Saving key: '{}' with value: '{}'", key, value);
        redisTemplate.opsForValue().set(key, value);
        log.info("✅ Key '{}' saved successfully.", key);
    }

    // 문자열 조회
    public String getString(String key) {
        log.info("\n\n\n");        
        log.info("🔍 Fetching value for key: '{}'", key);
        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            log.info("📦 Value for key '{}': '{}'", key, value);
        } else {
            log.warn("⚠️ No value found for key: '{}'", key);
        }
        return value;
    }

    // 키 삭제
    public void deleteKey(String key) {
        log.info("\n\n\n");        
        log.info("🗑️ Deleting key: '{}'", key);
        boolean deleted = Boolean.TRUE.equals(redisTemplate.delete(key));
        if (deleted) {
            log.info("❌ Key '{}' deleted.", key);
        } else {
            log.warn("⚠️ Failed to delete key: '{}'", key);
        }
    }

    // 전체 키와 값 조회
    public List<Map<String, String>> getAllKeysAndValues() {
        log.info("\n\n\n");
        log.info("📂 Fetching all keys and values...");        
        Set<String> keys = redisTemplate.keys("*");
        List<Map<String, String>> result = new ArrayList<>();

        if (keys != null) {
            for (String key : keys) {
                if (redisTemplate.type(key) == DataType.STRING) {
                    String value = redisTemplate.opsForValue().get(key);
                    result.add(Map.of("key", key, "value", value));
                    log.info("📄 Retrieved - key: '{}', value: '{}'", key, value);
                } else {
                    log.info("⏭️ Skipping non-string key: '{}' (type: {})", key, redisTemplate.type(key));
                }
            }
        }

        return result;
    }
}
