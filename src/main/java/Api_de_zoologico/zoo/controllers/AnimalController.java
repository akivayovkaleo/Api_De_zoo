package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.AnimalDto;
import Api_de_zoologico.zoo.models.Animal;
import Api_de_zoologico.zoo.models.Especie;
import Api_de_zoologico.zoo.models.Veterinario;
import Api_de_zoologico.zoo.services.AnimalService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/animais")
public class AnimalController {
    private AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<List<Animal>> findAll() {
        return ResponseEntity.ok(animalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(animalService.findById(id));
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Animal não encontrado",
                            e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

    }
    @GetMapping("/idade")
    public ResponseEntity<List<Animal>>findByIdade(@RequestParam int idadeMin, @RequestParam int idadeMax) {
        return ResponseEntity.ok(animalService.findByIdade(idadeMin,idadeMax));
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Animal>> findByNome(@RequestParam String nome) {
        return ResponseEntity.ok(animalService.findByNome(nome));
    }

    @GetMapping("/especie")
    public ResponseEntity<List<Animal>> findByEspecie(@RequestParam String nome) {
        return ResponseEntity.ok(animalService.findByEspecie(nome));
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody AnimalDto animal) {
        return ResponseEntity.ok(animalService.create(animal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AnimalDto animal) {
        try{
            return ResponseEntity.ok(animalService.update(id, animal));
        }catch (RuntimeException e){
            return RespostaUtil.buildErrorResponse("Animal não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            animalService.delete(id);
            return ResponseEntity.ok("Animal removido");
        }catch (RuntimeException e){
            return RespostaUtil.buildErrorResponse("Animal não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
