package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.VisitanteDto;
import Api_de_zoologico.zoo.models.Visitante;
import Api_de_zoologico.zoo.services.VisitanteService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/visitantes")
@CrossOrigin(origins = "*")
public class VisitanteController {
    private final VisitanteService visitanteService;

    public VisitanteController(VisitanteService visitanteService) {
        this.visitanteService = visitanteService;
    }

    @PreAuthorize("hasRole('VISITANTE') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        try {
            if (nome != null && !nome.trim().isEmpty()) {
                return ResponseEntity.ok(visitanteService.findByNome(nome));
            }
            if (cpf != null && !cpf.trim().isEmpty()) {
                return ResponseEntity.ok(visitanteService.findByCpf(cpf));
            }
            if (dataInicio != null && dataFim != null) {
                return ResponseEntity.ok(visitanteService.findByFaixaEtaria(dataInicio, dataFim));
            }
            return ResponseEntity.ok(visitanteService.findAll());
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao filtrar visitantes",
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar visitantes",
                    "Ocorreu um erro inesperado ao buscar visitantes: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('VISITANTE') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Visitante visitante = visitanteService.findById(id);
            return ResponseEntity.ok(visitante);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Visitante não encontrado",
                    "Não foi possível encontrar o visitante com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar visitante",
                    "Ocorreu um erro inesperado ao buscar o visitante: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('VISITANTE') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VisitanteDto visitanteDto) {
        try {
            Visitante visitanteCriado = visitanteService.create(visitanteDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(visitanteCriado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao criar visitante",
                    "Não foi possível criar o visitante. " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao criar o visitante: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('VISITANTE') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody VisitanteDto visitanteDto) {
        try {
            Visitante visitanteAtualizado = visitanteService.update(id, visitanteDto);
            return ResponseEntity.ok(visitanteAtualizado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao atualizar visitante",
                    "Não foi possível atualizar o visitante com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao atualizar o visitante: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('VISITANTE') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            visitanteService.delete(id);
            return ResponseEntity.ok().body(new MensagemResponse("Visitante removido com sucesso"));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao remover visitante",
                    "Não foi possível remover o visitante com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao remover o visitante: " + e.getMessage(),
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