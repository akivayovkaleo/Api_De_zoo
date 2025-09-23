package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.VeterinarioDto;
import Api_de_zoologico.zoo.models.Veterinario;
import Api_de_zoologico.zoo.services.VeterinarioService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veterinarios")
@CrossOrigin(origins = "*")
public class VeterinarioController {
    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String especialidade,
            @RequestParam(required = false) String nome) {
        try {
            if (especialidade != null && !especialidade.trim().isEmpty()) {
                return ResponseEntity.ok(veterinarioService.findByEspecialidade(especialidade));
            }
            if (nome != null && !nome.trim().isEmpty()) {
                return ResponseEntity.ok(veterinarioService.findByNome(nome));
            }
            return ResponseEntity.ok(veterinarioService.findAll());
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao filtrar veterinários",
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar veterinários",
                    "Ocorreu um erro inesperado ao buscar veterinários: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Veterinario veterinario = veterinarioService.findById(id);
            return ResponseEntity.ok(veterinario);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Veterinário não encontrado",
                    "Não foi possível encontrar o veterinário com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar veterinário",
                    "Ocorreu um erro inesperado ao buscar o veterinário: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VeterinarioDto veterinarioDto) {
        try {
            Veterinario veterinarioCriado = veterinarioService.create(veterinarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioCriado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao criar veterinário",
                    "Não foi possível criar o veterinário. " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao criar o veterinário: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody VeterinarioDto veterinarioDto) {
        try {
            Veterinario veterinarioAtualizado = veterinarioService.update(id, veterinarioDto);
            return ResponseEntity.ok(veterinarioAtualizado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao atualizar veterinário",
                    "Não foi possível atualizar o veterinário com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao atualizar o veterinário: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            veterinarioService.delete(id);
            return ResponseEntity.ok().body(new MensagemResponse("Veterinário removido com sucesso"));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao remover veterinário",
                    "Não foi possível remover o veterinário com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao remover o veterinário: " + e.getMessage(),
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