package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.models.ApiResponse;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import com.fasterxml.jackson.core.JsonParseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionController {

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
//        ApiResponse<Object> body = RespostaUtil.error("Ocorreu um erro inesperado", "Erro de Runtime", 1001, request.getRequestURI());
//        return ResponseEntity.status(500).body(body);
//    }

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,HttpServletRequest request) {
        String mensagem = "Verifique a sintaxe e os campos enviados Do JSON.";

        if (ex.getCause() instanceof JsonParseException parseException) {
            mensagem += " Detalhe: " + parseException.getOriginalMessage();
        }

        ApiResponse<Object> body = RespostaUtil.error(
                mensagem,
                "Erro de parse de JSON",
                400,
                request.getRequestURI()
        );

        return ResponseEntity.status(400).body(body);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        ApiResponse<Object> body = RespostaUtil.error(
                "Página não encontrada",
                "O servidor não conseguiu encontrar a página solicitada",
                404,
                request.getRequestURI()
        );
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        ApiResponse<Object> body = RespostaUtil.error(
                "Método HTTP não permitido",
                "Certifique-se de que está a utilizar o método HTTP correto para o recurso que está a tentar aceder ou enviar dados",
                405,
                request.getRequestURI()
        );
        return ResponseEntity.status(405).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex, HttpServletRequest request) {
        ApiResponse<Object> body = RespostaUtil.error(
                ex.getMessage(),
                "Erro inesperado",
                500,
                request.getRequestURI()
        );
        return ResponseEntity.status(500).body(body);
    }

}