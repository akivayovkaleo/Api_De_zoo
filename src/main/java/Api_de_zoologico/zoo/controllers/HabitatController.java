package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.HabitatDto;
import Api_de_zoologico.zoo.models.Habitat;
import Api_de_zoologico.zoo.services.HabitatService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habitats")
@CrossOrigin(origins = "*")
public class HabitatController {
    private final HabitatService habitatService;

    public HabitatController(HabitatService habitatService) {
        this.habitatService = habitatService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Habitat> habitats = habitatService.findAll();
            return ResponseEntity.ok(habitats);
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar habitats",
                    "Não foi possível recuperar a lista de habitats: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Habitat habitat = habitatService.findById(id);
            return ResponseEntity.ok(habitat);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Habitat não encontrado",
                    "Não foi possível encontrar o habitat com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar habitat",
                    "Ocorreu um erro inesperado ao buscar o habitat: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/tipo")
    public ResponseEntity<?> findByTipo(@RequestParam String tipo) {
        try {
            List<Habitat> habitats = habitatService.findByTipo(tipo);
            return ResponseEntity.ok(habitats);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar habitats por tipo",
                    "Não foi possível encontrar habitats do tipo '" + tipo + "': " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao filtrar habitats",
                    "Ocorreu um erro inesperado ao filtrar habitats: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/nome")
    public ResponseEntity<?> findByNome(@RequestParam String nome) {
        try {
            List<Habitat> habitats = habitatService.findByNome(nome);
            return ResponseEntity.ok(habitats);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar habitats por nome",
                    "Não foi possível encontrar habitats com o nome '" + nome + "': " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao filtrar habitats",
                    "Ocorreu um erro inesperado ao filtrar habitats: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody HabitatDto habitatDto) {
        try {
            Habitat habitatCriado = habitatService.create(habitatDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(habitatCriado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao criar habitat",
                    "Não foi possível criar o habitat. " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao criar o habitat: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody HabitatDto habitatDto) {
        try {
            Habitat habitatAtualizado = habitatService.update(id, habitatDto);
            return ResponseEntity.ok(habitatAtualizado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao atualizar habitat",
                    "Não foi possível atualizar o habitat com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao atualizar o habitat: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            habitatService.delete(id);
            return ResponseEntity.ok().body(new MensagemResponse("Habitat removido com sucesso"));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao remover habitat",
                    "Não foi possível remover o habitat com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao remover o habitat: " + e.getMessage(),
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