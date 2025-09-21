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

import java.util.List;

@RestController
@RequestMapping("/animais")
@CrossOrigin(origins = "*")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Operation(summary = "Lista todos os animais")
    @ApiResponse(responseCode = "200", description = "Animal encontrado com sucesso")
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Animal> animais = animalService.findAll();
            return ResponseEntity.ok(animais);
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar animais",
                    "Não foi possível recuperar a lista de animais: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Operation(summary = "Busca um animal pelo Id")
    @ApiResponse(responseCode = "200", description = "Animal encontrado com sucesso")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {

            return ResponseEntity.ok(animalService.findById(id));
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Animal não encontrado",
                            e.getMessage(), HttpStatus.NOT_FOUND);

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

    @Operation(summary = "Cria um novo animal")
    @ApiResponse(responseCode = "200", description = "Animal criado com sucesso")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody AnimalDto animal) {
        try {
            Animal animalCriado = animalService.create(animal);
            return ResponseEntity.status(HttpStatus.CREATED).body(animalCriado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao criar animal",
                    "Não foi possível criar o animal. " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao criar o animal: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Operation(summary = "Atualiza um animal pelo Id")
    @ApiResponse(responseCode = "200", description = "Animal atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody AnimalDto animal) {
        try {
            Animal animalAtualizado = animalService.update(id, animal);
            return ResponseEntity.ok(animalAtualizado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao atualizar animal",
                    "Não foi possível atualizar o animal com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao atualizar o animal: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Operation(summary = "Deleta um animal pelo Id")
    @ApiResponse(responseCode = "200", description = "Animal deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            animalService.delete(id);
            return ResponseEntity.ok().body(new MensagemResponse("Animal removido com sucesso"));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao remover animal",
                    "Não foi possível remover o animal com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao remover o animal: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public static class MensagemResponse {
        private String mensagem;

        public MensagemResponse(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}