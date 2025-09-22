package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.models.ApiResponse;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        ApiResponse<Object> body = RespostaUtil.error("Ocorreu um erro inesperado", "Erro de Runtime", 1001, request.getRequestURI());
        return ResponseEntity.status(500).body(body);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        ApiResponse<Object> body = RespostaUtil.error(
                ex.getMessage(),
                "Conflito no estado do recurso",
                409,
                request.getRequestURI()
        );
        return ResponseEntity.status(409).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ApiResponse<Object> body = RespostaUtil.error(ex.getMessage(), "Requisição inválida", 400, request.getRequestURI());
        return ResponseEntity.status(400).body(body);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        ApiResponse<Object> body = RespostaUtil.error(ex.getMessage(), "Recurso não encontrado", 404, request.getRequestURI());
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        ApiResponse<Object> body = RespostaUtil.error(errors, "Erro de validação", 400, request.getRequestURI());
        return ResponseEntity.status(400).body(body);
    }

}