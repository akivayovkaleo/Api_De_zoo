package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.AlimentacaoDto;
import Api_de_zoologico.zoo.models.Alimentacao;
import Api_de_zoologico.zoo.services.AlimentacaoService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/alimentacoes")
@CrossOrigin(origins = "*")
public class AlimentacaoController {
    private final AlimentacaoService alimentacaoService;

    public AlimentacaoController(AlimentacaoService alimentacaoService) {
        this.alimentacaoService = alimentacaoService;
    }

    @PreAuthorize("hasRole('ROLE_CUIDADOR') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String tipoComida,
            @RequestParam(required = false) Long animalId) {
        try {
            if (tipoComida != null && !tipoComida.trim().isEmpty()) {
                return ResponseEntity.ok(alimentacaoService.findByTipoComida(tipoComida));
            }
            if (animalId != null) {
                return ResponseEntity.ok(alimentacaoService.findByAnimalId(animalId));
            }
            return ResponseEntity.ok(alimentacaoService.findAll());
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao filtrar alimentações",
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar alimentações",
                    "Ocorreu um erro inesperado ao buscar alimentações: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_CUIDADOR') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Alimentacao alimentacao = alimentacaoService.findById(id);
            return ResponseEntity.ok(alimentacao);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Alimentação não encontrada",
                    "Não foi possível encontrar a alimentação com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar alimentação",
                    "Ocorreu um erro inesperado ao buscar a alimentação: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_CUIDADOR') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AlimentacaoDto alimentacaoDto) {
        try {
            Alimentacao alimentacaoCriada = alimentacaoService.create(alimentacaoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(alimentacaoCriada);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao criar alimentação",
                    "Não foi possível criar a alimentação. " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao criar a alimentação: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_CUIDADOR') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody AlimentacaoDto alimentacaoDto) {
        try {
            Alimentacao alimentacaoAtualizada = alimentacaoService.update(id, alimentacaoDto);
            return ResponseEntity.ok(alimentacaoAtualizada);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao atualizar alimentação",
                    "Não foi possível atualizar a alimentação com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao atualizar a alimentação: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_CUIDADOR') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            alimentacaoService.delete(id);
            return ResponseEntity.ok().body(new MensagemResponse("Alimentação removida com sucesso"));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao remover alimentação",
                    "Não foi possível remover a alimentação com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao remover a alimentação: " + e.getMessage(),
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