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
    @Operation(summary = "Filtra animais por faixa etária")
    @ApiResponse(responseCode = "200", description = "Lista de animais retornada com sucesso")
    public ResponseEntity<?> findByIdade(
            @RequestParam int idadeMin,
            @RequestParam int idadeMax
    ) {
        try {
            return ResponseEntity.ok(animalService.findByIdade(idadeMin, idadeMax));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Falha ao encontrar animais na faixa etária de " + idadeMin + " a " + idadeMax,
                    e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @GetMapping("/nome")
    @Operation(summary = "Filtra animais por nome")
    @ApiResponse(responseCode = "200", description = "Lista de animais retornada com sucesso")
    public ResponseEntity<?> findBynName(
            @RequestParam String nome
    ) {
        try {
            return ResponseEntity.ok(animalService.findByNome(nome));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Falha ao encontrar animais com nome "+ nome,
                    e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @GetMapping("/especie")
    @Operation(summary = "Filtra animais por especie")
    @ApiResponse(responseCode = "200", description = "Lista de animais retornada com sucesso")
    public ResponseEntity<?> findByEsepecie(
            @RequestParam String especie
    ) {
        try {
            return ResponseEntity.ok(animalService.findByEspecie(especie));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Falha ao encontrar animais da especie "+ especie,
                    e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        }
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
