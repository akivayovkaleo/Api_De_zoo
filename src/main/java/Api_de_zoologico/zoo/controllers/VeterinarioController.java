package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.models.Veterinario;
import Api_de_zoologico.zoo.services.VeterinarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {
    private VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping()
    public ResponseEntity<List<Veterinario>> getAll(){
        return ResponseEntity.ok(veterinarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(veterinarioService.findById(id));
        }catch (RuntimeException e){
            return buildErrorResponse("Veterinário não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/especialidade")
    public ResponseEntity<?> getByEspecialidade(@RequestParam  String especialidade) {
        try{
            return ResponseEntity.ok(veterinarioService.findByEspecialidade(especialidade));
        }catch (RuntimeException e){
            return buildErrorResponse("Veterinário não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Veterinario veterinario) {
        return ResponseEntity.ok(veterinarioService.create(veterinario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Veterinario veterinario) {
        try{
            return ResponseEntity.ok(veterinarioService.update(id, veterinario));
        }catch (RuntimeException e){
            return buildErrorResponse("Veterinário não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            veterinarioService.delete(id);
            return ResponseEntity.ok("Veterinario removido");
        }catch (RuntimeException e){
            return buildErrorResponse("Veterinário não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    private ResponseEntity<Map<String, Object>> buildErrorResponse(String error, String message, HttpStatus status){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }

}
