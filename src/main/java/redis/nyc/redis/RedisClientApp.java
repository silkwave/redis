package redis.nyc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import redis.nyc.redis.httpClient.RedisHttpClient;

@Slf4j // Lombok 로그 어노테이션
@SpringBootApplication
public class RedisClientApp implements CommandLineRunner {

    @Autowired
    private RedisHttpClient redisHttpClient;

    public static void main(String[] args) {
        SpringApplication.run(RedisClientApp.class, args);
    }

    @Override
    public void run(String... args) {
        String key = "TESTKEY";
        String initialValue = "Hello🎈World!";
        String updatedValue = "Updated🎈Value";

        performRedisOperations(key, initialValue, updatedValue);
    }

    private void performRedisOperations(String key, String initialValue, String updatedValue) {
        log.info("🔷🔷🔷🔷🔷🔷🔷🔷🔷🔷");
        log.info("🧩 [START] Redis Operations");
        log.info("🔷🔷🔷🔷🔷🔷🔷🔷🔷🔷");

        log.info("🚀 [CREATE] Key: '{}', Value: '{}'", key, initialValue);
        redisHttpClient.createString(key, initialValue);

        log.info("🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩");

        log.info("🔍 [READ] Key: '{}'", key);
        redisHttpClient.readString(key);

        log.info("🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩");

        log.info("♻️ [UPDATE] Key: '{}', New Value: '{}'", key, updatedValue);
        redisHttpClient.updateString(key, updatedValue);

        log.info("🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩");

        log.info("🗑️ [DELETE] Key: '{}'", key);
        redisHttpClient.deleteKey(key);

        log.info("🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩");

        log.info("📦 [FETCH ALL] All Redis key-value pairs:");
        redisHttpClient.getAllKeysAndValues();

        log.info("🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩🧩");

        log.info("✅ [DONE] All Redis operations completed successfully.");
        log.info("🔷🔷🔷🔷🔷🔷🔷🔷🔷🔷");
    }

}
