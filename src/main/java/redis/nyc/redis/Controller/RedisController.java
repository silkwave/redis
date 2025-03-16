package redis.nyc.redis.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import redis.nyc.redis.Service.RedisService;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    // Create: Save a new string to Redis
    @PostMapping("/create")
    public ResponseEntity<String> createString(@RequestParam String key, @RequestParam String value) {
        redisService.saveString(key, value);
        return ResponseEntity.ok("Created key: " + key + " with value: " + value);
    }

    // Read: Get a string from Redis
    @GetMapping("/read")
    public ResponseEntity<String> readString(@RequestParam String key) {
        String value = redisService.getString(key);
        if (value != null) {
            return ResponseEntity.ok("Value for key " + key + ": " + value);
        } else {
            return ResponseEntity.status(404).body("Key not found: " + key);
        }
    }

    // Update: Update an existing key's value in Redis
    @PutMapping("/update")
    public ResponseEntity<String> updateString(@RequestParam String key, @RequestParam String value) {
        String existingValue = redisService.getString(key);
        if (existingValue != null) {
            redisService.saveString(key, value);
            return ResponseEntity.ok("Updated key: " + key + " with new value: " + value);
        } else {
            return ResponseEntity.status(404).body("Key not found: " + key);
        }
    }

    // Delete: Delete a key from Redis
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteKey(@RequestParam String key) {
        String value = redisService.getString(key);
        if (value != null) {
            redisService.deleteKey(key);
            return ResponseEntity.ok("Deleted key: " + key);
        } else {
            return ResponseEntity.status(404).body("Key not found: " + key);
        }
    }

    // Controller에서 JSON 응답으로 반환
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, String>>> getAllKeysAndValues() {
        List<Map<String, String>> keysAndValues = redisService.getAllKeysAndValues();
        return ResponseEntity.ok(keysAndValues);
    }
}
