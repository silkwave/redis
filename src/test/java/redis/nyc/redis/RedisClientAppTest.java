package redis.nyc.redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import redis.nyc.redis.httpClient.RedisHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public class RedisClientAppTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisClientAppTest.class);

    @Mock
    private RedisHttpClient redisHttpClient;

    @InjectMocks
    private RedisClientApp redisClientApp;

    @BeforeEach
    public void setup() {
        // Initialization before each test if necessary
    }

    @Test
    public void testPerformRedisOperations() {
        String key = "TESTKEY";
        String initialValue = "Hello_World!";
        String updatedValue = "Updated_Value";

        // Simulate redis operations
        doNothing().when(redisHttpClient).createString(key, initialValue);
        doNothing().when(redisHttpClient).readString(key);
        doNothing().when(redisHttpClient).updateString(key, updatedValue);
        doNothing().when(redisHttpClient).deleteKey(key);
        doNothing().when(redisHttpClient).getAllKeysAndValues();

        // Call the method we are testing
        redisClientApp.performRedisOperations(key, initialValue, updatedValue);

        // Verify interactions with the mock
        verify(redisHttpClient).createString(key, initialValue);
        verify(redisHttpClient).readString(key);
        verify(redisHttpClient).updateString(key, updatedValue);
        verify(redisHttpClient).deleteKey(key);
        verify(redisHttpClient).getAllKeysAndValues();

        // Log the success
        logger.info("Test completed successfully.");
    }
}
