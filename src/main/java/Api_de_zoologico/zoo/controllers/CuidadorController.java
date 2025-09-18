package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.services.CuidadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cuidadores")
public class CuidadorController {
    private CuidadorService cuidadorService;

    public CuidadorController(CuidadorService cuidadorService) {
        this.cuidadorService = cuidadorService;
    }

    @PostMapping
    public ResponseEntity<Cuidador> create(@RequestBody Cuidador cuidador) {
        return ResponseEntity.ok(cuidadorService.create(cuidador));
    }

    @GetMapping
    public ResponseEntity<List<Cuidador>> listar(
            @RequestParam(required = false) String especialidade,
            @RequestParam(required = false) String turno
    ) {
        if (especialidade != null) return ResponseEntity.ok(cuidadorService.findByEspecialidade(especialidade));
        if (turno != null) return ResponseEntity.ok(cuidadorService.findByTurno(turno));
        return ResponseEntity.ok(cuidadorService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cuidador cuidador) {
        try {
            return ResponseEntity.ok(cuidadorService.update(id, cuidador));
        }catch (RuntimeException e) {
            return buildErrorResponse("Cuidador não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        cuidadorService.delete(id);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String error, String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }
}
