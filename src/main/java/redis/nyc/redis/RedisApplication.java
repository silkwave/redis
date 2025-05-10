package redis.nyc.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j // Lombok을 사용하여 Logger 자동 생성
@SpringBootApplication
public class RedisApplication  {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

}
