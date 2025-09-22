package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.AnimalDto;
import Api_de_zoologico.zoo.models.Animal;
import Api_de_zoologico.zoo.services.AnimalService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Operation(summary = "Lista todos os animais")
    @GetMapping
    public ResponseEntity<?> listarTodos(
            @Parameter(description = "Idade mínima") @RequestParam(required = false) Integer idadeMin,
            @Parameter(description = "Idade máxima") @RequestParam(required = false) Integer idadeMax,
            @Parameter(description = "Filtra por nome") @RequestParam(required = false) String nome,
            @Parameter(description = "Filtra por espécie") @RequestParam(required = false) String especie,
            HttpServletRequest request
    ) {
        List<Animal> animais;
        if(idadeMin != null && idadeMax != null) animais = animalService.findByIdade(idadeMin, idadeMax);
        else if (nome != null) animais = animalService.findByNome(nome);
        else if (especie != null) animais = animalService.findByEspecie(especie);
        else animais = animalService.findAll();

        return ResponseEntity.ok(
                RespostaUtil.success(animais, "Lista de animais retornada com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Busca um animal pelo Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "Id do animal a ser retornado") @PathVariable Long id,
            HttpServletRequest request
    ) {
        Animal animal = animalService.findById(id);
        return ResponseEntity.ok(
                RespostaUtil.success(animal, "Animal retornado com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Filtra animais por faixa etária")
    @GetMapping("/idade")
    public ResponseEntity<?> listarPorIdade(
            @Parameter(description = "Idade mínima") @RequestParam int idadeMin,
            @Parameter(description = "Idade máxima") @RequestParam int idadeMax,
            HttpServletRequest request
    ) {
        List<Animal> animais = animalService.findByIdade(idadeMin, idadeMax);
        return ResponseEntity.ok(
                RespostaUtil.success(animais, "Lista de animais retornada com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Filtra animais por nome")
    @GetMapping("/nome")
    public ResponseEntity<?> listarPorNome(
            @Parameter(description = "Nome do animal") @RequestParam String nome,
            HttpServletRequest request
    ) {
        List<Animal> animais = animalService.findByNome(nome);
        return ResponseEntity.ok(
                RespostaUtil.success(animais, "Lista de animais retornada com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Filtra animais por espécie")
    @GetMapping("/especie")
    public ResponseEntity<?> listarPorEspecie(
            @Parameter(description = "Nome da espécie") @RequestParam String especie,
            HttpServletRequest request
    ) {
        List<Animal> animais = animalService.findByEspecie(especie);
        return ResponseEntity.ok(
                RespostaUtil.success(animais, "Lista de animais retornada com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Cria um novo animal")
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody AnimalDto animalDto, HttpServletRequest request) {
        Animal animal = animalService.create(animalDto);
        return ResponseEntity.ok(
                RespostaUtil.success(animal, "Animal criado com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Atualiza um animal pelo Id")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "Id do animal a ser atualizado") @PathVariable Long id,
            @Valid @RequestBody AnimalDto animalDto,
            HttpServletRequest request
    ) {
        Animal animal = animalService.update(id, animalDto);
        return ResponseEntity.ok(
                RespostaUtil.success(animal, "Animal atualizado com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Deleta um animal pelo Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "Id do animal a ser deletado") @PathVariable Long id,
            HttpServletRequest request
    ) {
        animalService.delete(id);
        return ResponseEntity.ok(
                RespostaUtil.success(null, "Animal deletado com sucesso", request.getRequestURI())
        );
    }
}
