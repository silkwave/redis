package redis.nyc.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * 이 클래스는 Redis 웹 애플리케이션의 메인 진입점입니다.
 * Spring Boot 애플리케이션을 초기화하고 실행하는 역할을 합니다.
 */
@Slf4j // Lombok을 사용하여 Logger 자동 생성
@SpringBootApplication
public class RedisApplication  {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

}
