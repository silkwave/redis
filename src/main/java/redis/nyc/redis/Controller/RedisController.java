package redis.nyc.redis.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import redis.nyc.redis.Service.RedisService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    // CREATE - POST /api/redis/{key}?value=...
    @PostMapping("/{key}")
    public ResponseEntity<String> createString(
            @PathVariable @NonNull String key,
            @RequestParam("value") @NonNull String value) {

        redisService.saveString(key, value);
        return ResponseEntity.ok("Created key: " + key + " with value: " + value);
    }

    // READ - GET /api/redis/{key}
    @GetMapping("/{key}")
    public ResponseEntity<String> readString(@PathVariable @NonNull String key) {
        Optional<String> valueOpt = redisService.getString(key);
        return valueOpt
                .map(v -> ResponseEntity.ok("Value for key " + key + ": " + v))
                .orElse(ResponseEntity.status(404).body("Key not found: " + key));
    }

    // UPDATE - PUT /api/redis/{key}?value=...
    @PutMapping("/{key}")
    public ResponseEntity<String> updateString(
            @PathVariable @NonNull String key,
            @RequestParam("value") @NonNull String value) {

        boolean updated = redisService.updateString(key, value);
        return updated
                ? ResponseEntity.ok("Updated key: " + key + " with new value: " + value)
                : ResponseEntity.status(404).body("Key not found, not updated: " + key);
    }

    // DELETE - DELETE /api/redis/{key}
    @DeleteMapping("/{key}")
    public ResponseEntity<String> deleteKey(@PathVariable @NonNull String key) {
        boolean deleted = redisService.deleteKey(key);
        return deleted
                ? ResponseEntity.ok("Deleted key: " + key)
                : ResponseEntity.status(404).body("Key not found or could not be deleted: " + key);
    }

    // GET ALL - GET /api/redis/all
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, String>>> getAllKeysAndValues() {
        List<Map<String, String>> data = redisService.getAllKeysAndValues();
        return ResponseEntity.ok(data);
    }
}