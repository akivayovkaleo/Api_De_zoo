package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.AnimalDto;
import Api_de_zoologico.zoo.models.Animal;
import Api_de_zoologico.zoo.services.AnimalService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN') or hasRole('ROLE_CUIDADOR') or hasRole('ROLE_VETERINARIO')")
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

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUIDADOR') or hasRole('ROLE_VETERINARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Animal animal = animalService.findById(id);
            return ResponseEntity.ok(animal);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Animal não encontrado",
                    "Não foi possível encontrar o animal com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping("/idade")
    public ResponseEntity<?> findByIdade(
            @RequestParam int idadeMin,
            @RequestParam int idadeMax) {
        try {
            List<Animal> animais = animalService.findByIdadeBetween(idadeMin, idadeMax);
            return ResponseEntity.ok(animais);
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao filtrar animais por idade",
                    "Não foi possível filtrar animais entre " + idadeMin + " e " + idadeMax + " anos: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/nome")
    public ResponseEntity<?> findByNome(@RequestParam String nome) {
        try {
            List<Animal> animais = animalService.findByNome(nome);
            return ResponseEntity.ok(animais);
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar animais por nome",
                    "Não foi possível encontrar animais com o nome: " + nome + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/especie")
    public ResponseEntity<?> findByEspecie(@RequestParam String nome) {
        try {
            List<Animal> animais = animalService.findByEspecie(nome);
            return ResponseEntity.ok(animais);
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar animais por espécie",
                    "Não foi possível encontrar animais da espécie: " + nome + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

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