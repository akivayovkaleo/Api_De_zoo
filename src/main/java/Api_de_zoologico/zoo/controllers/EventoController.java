package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.EventoDto;
import Api_de_zoologico.zoo.models.Evento;
import Api_de_zoologico.zoo.services.EventoService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/eventos")
@CrossOrigin(origins = "*")
public class EventoController {
    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam(required = false) Boolean lotados,
            @RequestParam(required = false) Boolean futuros) {
        try {
            if (nome != null && !nome.trim().isEmpty()) {
                return ResponseEntity.ok(eventoService.findByNome(nome));
            }
            if (dataInicio != null && dataFim != null) {
                return ResponseEntity.ok(eventoService.findByPeriodo(dataInicio, dataFim));
            }
            if (Boolean.TRUE.equals(lotados)) {
                return ResponseEntity.ok(eventoService.findEventosLotados());
            }
            if (Boolean.TRUE.equals(futuros)) {
                return ResponseEntity.ok(eventoService.findEventosFuturos());
            }
            return ResponseEntity.ok(eventoService.findAll());
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao filtrar eventos",
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar eventos",
                    "Ocorreu um erro inesperado ao buscar eventos: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Evento evento = eventoService.findById(id);
            return ResponseEntity.ok(evento);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Evento não encontrado",
                    "Não foi possível encontrar o evento com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar evento",
                    "Ocorreu um erro inesperado ao buscar o evento: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody EventoDto eventoDto) {
        try {
            Evento eventoCriado = eventoService.create(eventoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventoCriado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao criar evento",
                    "Não foi possível criar o evento. " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao criar o evento: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody EventoDto eventoDto) {
        try {
            Evento eventoAtualizado = eventoService.update(id, eventoDto);
            return ResponseEntity.ok(eventoAtualizado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao atualizar evento",
                    "Não foi possível atualizar o evento com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao atualizar o evento: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            eventoService.delete(id);
            return ResponseEntity.ok().body(new MensagemResponse("Evento removido com sucesso"));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao remover evento",
                    "Não foi possível remover o evento com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao remover o evento: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // Endpoints para gerenciamento de inscrições
    @PostMapping("/{eventoId}/visitantes/{visitanteId}")
    public ResponseEntity<?> adicionarVisitante(@PathVariable Long eventoId, @PathVariable Long visitanteId) {
        try {
            Evento evento = eventoService.adicionarVisitante(eventoId, visitanteId);
            return ResponseEntity.ok(evento);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao adicionar visitante",
                    "Não foi possível adicionar o visitante ao evento. " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{eventoId}/visitantes/{visitanteId}")
    public ResponseEntity<?> removerVisitante(@PathVariable Long eventoId, @PathVariable Long visitanteId) {
        try {
            Evento evento = eventoService.removerVisitante(eventoId, visitanteId);
            return ResponseEntity.ok(evento);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao remover visitante",
                    "Não foi possível remover o visitante do evento. " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // Classe auxiliar para mensagens de sucesso
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