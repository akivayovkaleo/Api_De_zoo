package Api_de_zoologico.zoo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RespostaUtil {
    public static ResponseEntity<Map<String, Object>> buildErrorResponse(String err, String messg, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", err);
        body.put("message", messg);

        return ResponseEntity.status(status).body(body);
    }
}
