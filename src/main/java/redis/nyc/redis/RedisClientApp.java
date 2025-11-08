package redis.nyc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import redis.nyc.redis.httpClient.RedisHttpClient;

/**
 * 이 클래스는 Redis 클라이언트 애플리케이션으로,
 * 애플리케이션 시작 시 Redis의 CRUD(생성, 읽기, 업데이트, 삭제) 작업을 시연합니다.
 * CommandLineRunner를 구현하여 Spring Boot 애플리케이션이 시작된 후 특정 코드를 실행합니다.
 */
@Slf4j // Lombok 로그 어노테이션
@SpringBootApplication
public class RedisClientApp implements CommandLineRunner {

    @Autowired
    private RedisHttpClient redisHttpClient;

    public static void main(String[] args) {
        SpringApplication.run(RedisClientApp.class, args);
    }

    /**
     * 애플리케이션 시작 후 실행되는 메서드입니다.
     * Redis 키-값 쌍에 대한 일련의 작업을 수행합니다.
     * @param args 커맨드 라인 인자
     */
    @Override
    public void run(String... args) {
        String key = "TESTKEY";
        String initialValue = "Hello_World";
        String updatedValue = "Hello_World_Updated";

        performRedisOperations(key, initialValue, updatedValue);
    }

    /**
     * Redis에 대한 CRUD 작업을 수행하는 헬퍼 메서드입니다.
     * @param key Redis 키
     * @param initialValue 생성 및 초기 업데이트에 사용될 값
     * @param updatedValue 업데이트에 사용될 새로운 값
     */
    public  void performRedisOperations(String key, String initialValue, String updatedValue) {
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
