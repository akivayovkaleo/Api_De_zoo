package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.services.CuidadorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Api_de_zoologico.zoo.utils.RespostaUtil.buildErrorResponse;

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

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(cuidadorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
            try {
                return ResponseEntity.ok(cuidadorService.findById(id));
            }catch (RuntimeException e) {
                return buildErrorResponse("Cuidador não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
            }

        }

    @GetMapping("/especialidade")
    public ResponseEntity<?> getByEspecialidade(@RequestParam String especialidade){
        try {
            return ResponseEntity.ok(cuidadorService.findByEspecialidade(especialidade));
        }catch (RuntimeException e) {
            return buildErrorResponse("Cuidador não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/turno")
    public ResponseEntity<?> getByTurno(@RequestParam String turno){
        try {
            return ResponseEntity.ok(cuidadorService.findByTurno(turno));
        }catch (RuntimeException e) {
            return buildErrorResponse("Cuidador não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cuidadorService.delete(id);
            return ResponseEntity.ok("Cuidador removido");
        }catch (DataIntegrityViolationException e) {
            return buildErrorResponse("Erro de integridade referencial",
                    "Não é possível deletar o cuidador porque existem animais vinculados a ele.",
                    HttpStatus.CONFLICT);}

        catch (RuntimeException e) {
            return buildErrorResponse("Cuidador não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }

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
