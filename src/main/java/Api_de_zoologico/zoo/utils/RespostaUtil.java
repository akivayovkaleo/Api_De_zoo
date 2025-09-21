package Api_de_zoologico.zoo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RespostaUtil {

    public static ResponseEntity<Map<String, Object>> buildErrorResponse(
            String titulo,
            String detalhe,
            HttpStatus status) {

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", status.value());
        resposta.put("error", status.getReasonPhrase());
        resposta.put("titulo", titulo);
        resposta.put("detalhe", detalhe);

        return ResponseEntity.status(status).body(resposta);
    }

    public static ResponseEntity<Map<String, Object>> buildSuccessResponse(
            String mensagem,
            Object dados,
            HttpStatus status) {

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", status.value());
        resposta.put("mensagem", mensagem);
        resposta.put("dados", dados);

        return ResponseEntity.status(status).body(resposta);
    }
}