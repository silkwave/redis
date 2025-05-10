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

    // Create - POST /api/redis/{key}
    @PostMapping("/{key}")
    public ResponseEntity<String> createString(@PathVariable("key") String key, @RequestParam("value") String value) {
        redisService.saveString(key, value);
        return ResponseEntity.ok("Created key: " + key + " with value: " + value);
    }

    // Read - GET /api/redis/{key}
    @GetMapping("/{key}")
    public ResponseEntity<String> readString(@PathVariable("key") String key) {
        String value = redisService.getString(key);
        if (value != null) {
            return ResponseEntity.ok("readString Value for key " + key + ": " + value);
        } else {
            return ResponseEntity.status(404).body("readString Key not found: " + key);
        }
    }

    // Update - PUT /api/redis/{key}
    @PutMapping("/{key}")
    public ResponseEntity<String> updateString(@PathVariable("key") String key, @RequestParam("value") String value) {
        if (redisService.getString(key) != null) {
            redisService.saveString(key, value);
            return ResponseEntity.ok("Updated key: " + key + " with new value: " + value);
        } else {
            return ResponseEntity.status(404).body("Key not found: " + key);
        }
    }

    // Delete - DELETE /api/redis/{key}
    @DeleteMapping("/{key}")
    public ResponseEntity<String> deleteKey(@PathVariable("key") String key) {
        if (redisService.getString(key) != null) {
            redisService.deleteKey(key);
            return ResponseEntity.ok("Deleted key: " + key);
        } else {
            return ResponseEntity.status(404).body("Key not found: " + key);
        }
    }

    // Get All Keys and Values - GET /api/redis/all
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, String>>> getAllKeysAndValues() {
        return ResponseEntity.ok(redisService.getAllKeysAndValues());
    }
}
