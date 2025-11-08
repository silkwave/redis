package redis.nyc.redis.httpClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RedisHttpClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/api/redis";

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private ResponseEntity<String> executeRequest(@NonNull String url, HttpMethod method) {
        return executeRequest(url, method, new HttpEntity<>(new HttpHeaders()));
    }

    private ResponseEntity<String> executeRequest(@NonNull String url, HttpMethod method, HttpEntity<String> entity) {
        try {
            log.info("▶️ Executing [{}] request to URL: 🔍[{}]", method.name(), url);
            return restTemplate.exchange(url, method, entity, String.class);
        } catch (RestClientException e) {
            log.error("❌ Request failed on {} request to \"{}\": {}",
                                method.name(), url, e.getMessage());
            return null;
        }
    }

    public void createString(String key, String value) {
        String url = baseUrl + "/" + encode(key) + "?value=" + encode(value);
        ResponseEntity<String> response = executeRequest(url, HttpMethod.POST);
        if (response != null)
            log.info("🆕 Create response: {}", response.getBody());
    }

    public void readString(String key) {
        String url = baseUrl + "/" + encode(key);
        ResponseEntity<String> response = executeRequest(url, HttpMethod.GET);
        if (response != null)
            log.info("📖 Read response: {}", response.getBody());
    }

    public void updateString(String key, String value) {
        String url = baseUrl + "/" + encode(key) + "?value=" + encode(value);
        ResponseEntity<String> response = executeRequest(url, HttpMethod.PUT);
        if (response != null)
            log.info("🔄 Update response: {}", response.getBody());
    }

    public void deleteKey(String key) {
        String url = baseUrl + "/" + encode(key);
        ResponseEntity<String> response = executeRequest(url, HttpMethod.DELETE);
        if (response != null)
            log.info("🗑️ Delete response: {}", response.getBody());
    }

    public void getAllKeysAndValues() {
        String url = baseUrl + "/all";
        ResponseEntity<String> response = executeRequest(url, HttpMethod.GET);
        if (response != null)
            log.info("📦 All Keys & Values: {}", response.getBody());
    }
}
