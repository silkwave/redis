package redis.nyc.redis.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public void saveString(@NonNull String key, @NonNull String value) {
        redisTemplate.opsForValue().set(key, value);
        log.info("Saved key: {}", key);
    }

    public Optional<String> getString(@NonNull String key) {
        String value = redisTemplate.opsForValue().get(key);
        log.info("Get key: {}, found: {}", key, value != null);
        return Optional.ofNullable(value);
    }

    public boolean updateString(@NonNull String key, @NonNull String value) {
        Boolean exists = redisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(exists)) {
            redisTemplate.opsForValue().set(key, value);
            log.info("Updated key: {}", key);
            return true;
        }
        log.warn("Update failed: key not found: {}", key);
        return false;
    }

    public boolean deleteKey(@NonNull String key) {
        Boolean deleted = redisTemplate.delete(key);
        boolean result = Boolean.TRUE.equals(deleted);
        log.info("Delete key: {} → {}", key, result);
        return result;
    }

    @SuppressWarnings("null")
    @NonNull
    public List<Map<String, String>> getAllKeysAndValues() {
        Set<String> keys = redisTemplate.keys("*");
        Set<String> nonNullKeys = Optional.ofNullable(keys).orElse(Collections.emptySet());

        return nonNullKeys.stream()
                .map(key -> {
                    String value = redisTemplate.opsForValue().get(key);
                    Map<String, String> entry = new HashMap<>();
                    entry.put("key", key);
                    entry.put("value", Objects.requireNonNullElse(value, "null"));
                    return entry;
                })
                .collect(Collectors.toList());
    }
}