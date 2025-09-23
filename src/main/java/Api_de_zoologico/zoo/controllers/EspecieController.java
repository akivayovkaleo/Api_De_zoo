package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.EspecieDto;
import Api_de_zoologico.zoo.models.Especie;
import Api_de_zoologico.zoo.services.EspecieService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/especies")
@CrossOrigin(origins = "*")
public class EspecieController {
    private final EspecieService especieService;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    @PreAuthorize("hasRole('ROLE_CUIDADOR') or hasRole('ROLE_VETERINARIO') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String familia,
            @RequestParam(required = false) String ordem,
            @RequestParam(required = false) String classe) {
        try {
            if (nome != null && !nome.trim().isEmpty()) {
                return ResponseEntity.ok(especieService.findByNome(nome));
            }
            if (familia != null && !familia.trim().isEmpty()) {
                return ResponseEntity.ok(especieService.findByFamilia(familia));
            }
            if (ordem != null && !ordem.trim().isEmpty()) {
                return ResponseEntity.ok(especieService.findByOrdem(ordem));
            }
            if (classe != null && !classe.trim().isEmpty()) {
                return ResponseEntity.ok(especieService.findByClasse(classe));
            }
            return ResponseEntity.ok(especieService.findAll());
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao filtrar espécies",
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar espécies",
                    "Ocorreu um erro inesperado ao buscar espécies: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_CUIDADOR') or hasRole('ROLE_VETERINARIO') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Especie especie = especieService.findById(id);
            return ResponseEntity.ok(especie);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Espécie não encontrada",
                    "Não foi possível encontrar a espécie com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar espécie",
                    "Ocorreu um erro inesperado ao buscar a espécie: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_CUIDADOR') or hasRole('ROLE_VETERINARIO') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody EspecieDto especieDto) {
        try {
            Especie especieCriada = especieService.create(especieDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(especieCriada);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao criar espécie",
                    "Não foi possível criar a espécie. " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao criar a espécie: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_CUIDADOR') or hasRole('ROLE_VETERINARIO') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody EspecieDto especieDto) {
        try {
            Especie especieAtualizada = especieService.update(id, especieDto);
            return ResponseEntity.ok(especieAtualizada);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao atualizar espécie",
                    "Não foi possível atualizar a espécie com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao atualizar a espécie: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_CUIDADOR') or hasRole('ROLE_VETERINARIO') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            especieService.delete(id);
            return ResponseEntity.ok().body(new MensagemResponse("Espécie removida com sucesso"));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao remover espécie",
                    "Não foi possível remover a espécie com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao remover a espécie: " + e.getMessage(),
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