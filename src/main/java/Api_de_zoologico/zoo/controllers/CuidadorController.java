package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.CuidadorDto;
import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.services.CuidadorService;

import org.springframework.dao.DataIntegrityViolationException;

import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Api_de_zoologico.zoo.utils.RespostaUtil.buildErrorResponse;


@RestController
@RequestMapping("/cuidadores")
@CrossOrigin(origins = "*")
public class CuidadorController {
    private final CuidadorService cuidadorService;

    public CuidadorController(CuidadorService cuidadorService) {
        this.cuidadorService = cuidadorService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String especialidade,
            @RequestParam(required = false) String turno,
            @RequestParam(required = false) String nome) {
        try {
            if (especialidade != null && !especialidade.trim().isEmpty()) {
                return ResponseEntity.ok(cuidadorService.findByEspecialidade(especialidade));
            }
            if (turno != null && !turno.trim().isEmpty()) {
                return ResponseEntity.ok(cuidadorService.findByTurno(turno));
            }
            if (nome != null && !nome.trim().isEmpty()) {
                return ResponseEntity.ok(cuidadorService.findByNome(nome));
            }
            return ResponseEntity.ok(cuidadorService.findAll());
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao filtrar cuidadores",
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar cuidadores",
                    "Ocorreu um erro inesperado ao buscar cuidadores: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Cuidador cuidador = cuidadorService.findById(id);
            return ResponseEntity.ok(cuidador);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Cuidador não encontrado",
                    "Não foi possível encontrar o cuidador com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar cuidador",
                    "Ocorreu um erro inesperado ao buscar o cuidador: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CuidadorDto cuidadorDto) {
        try {
            Cuidador cuidadorCriado = cuidadorService.create(cuidadorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cuidadorCriado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao criar cuidador",
                    "Não foi possível criar o cuidador. " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao criar o cuidador: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
            try {
                return ResponseEntity.ok(cuidadorService.findById(id));
            }catch (RuntimeException e) {
                return buildErrorResponse("Cuidador não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
            }

        }

    @GetMapping("/especialidade")
    public ResponseEntity<?> getByEspecialidade(@RequestParam String especialidade){
        try {
            return ResponseEntity.ok(cuidadorService.findByEspecialidade(especialidade));
        }catch (RuntimeException e) {
            return buildErrorResponse("Cuidador não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/turno")
    public ResponseEntity<?> getByTurno(@RequestParam String turno){
        try {
            return ResponseEntity.ok(cuidadorService.findByTurno(turno));
        }catch (RuntimeException e) {
            return buildErrorResponse("Cuidador não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody CuidadorDto cuidadorDto) {
        try {
            Cuidador cuidadorAtualizado = cuidadorService.update(id, cuidadorDto);
            return ResponseEntity.ok(cuidadorAtualizado);
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao atualizar cuidador",
                    "Não foi possível atualizar o cuidador com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao atualizar o cuidador: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cuidadorService.delete(id);

            return ResponseEntity.ok().body(new MensagemResponse("Cuidador removido com sucesso"));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao remover cuidador",
                    "Não foi possível remover o cuidador com ID: " + id + ". " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro interno do servidor",
                    "Ocorreu um erro inesperado ao remover o cuidador: " + e.getMessage(),
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