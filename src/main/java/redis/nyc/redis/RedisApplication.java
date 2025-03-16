package redis.nyc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import redis.nyc.redis.Service.RedisService;

@Slf4j // Lombok을 사용하여 Logger 자동 생성
@SpringBootApplication
public class RedisApplication implements CommandLineRunner {

    @Autowired
    private RedisService redisService;

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Redis에 데이터 저장
        redisService.saveString("user:name", "John Doe");

        // Redis에서 데이터 조회
        String value = redisService.getString("user:name");
        log.info("Retrieved from Redis: {}", value);
    }

}
