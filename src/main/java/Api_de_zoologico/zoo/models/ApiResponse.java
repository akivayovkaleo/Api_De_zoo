package Api_de_zoologico.zoo.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
    private int errorCode;
    private LocalDateTime timestamp;
    private String path;
}
