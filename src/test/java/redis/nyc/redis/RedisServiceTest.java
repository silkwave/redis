package redis.nyc.redis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import redis.nyc.redis.Service.RedisService;

@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    private static final String TEST_KEY = "test:key";
    private static final String TEST_VALUE = "Hello, Redis!";

    @BeforeEach
    void setUp() {
        redisService.saveString(TEST_KEY, TEST_VALUE);
    }

    @Test
    void 레디스_문자열_저장_및_조회_테스트() {
        // when
        String value = redisService.getString(TEST_KEY);

        // then
        assertThat(value).isEqualTo(TEST_VALUE);
    }

    @Test
    void 레디스_데이터_삭제_테스트() {
        // given
        redisService.saveString(TEST_KEY, TEST_VALUE);

        // when
        redisService.deleteKey(TEST_KEY);  
        String value = redisService.getString(TEST_KEY);

        // then
        assertThat(value).isNull();
    }
}
