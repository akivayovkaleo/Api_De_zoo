package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.EspecieDto;
import Api_de_zoologico.zoo.models.Especie;
import Api_de_zoologico.zoo.services.EspecieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/especies")
public class EspecieController {

    private final EspecieService especieService;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova espécie")
    @ApiResponse(responseCode = "200", description = "Espécie criada com sucesso")
    public ResponseEntity<Especie> create(@Valid @RequestBody EspecieDto dto) {
        return ResponseEntity.ok(especieService.criar(dto));
    }

    @GetMapping
    @Operation(summary = "Lista todas as espécies ou filtra por nome, família ou classe")
    @ApiResponse(responseCode = "200", description = "Lista de espécies retornada com sucesso")
    public ResponseEntity<List<Especie>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String familia,
            @RequestParam(required = false) String classe
    ) {
        if (nome != null) return ResponseEntity.ok(especieService.BuscarPorNome(nome));
        if (familia != null) return ResponseEntity.ok(especieService.BuscarPorFamilia(familia));
        if (classe != null) return ResponseEntity.ok(especieService.buscarPorClasse(classe));
        return ResponseEntity.ok(especieService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma espécie pelo ID")
    @ApiResponse(responseCode = "200", description = "Espécie encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Espécie não encontrada")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(especieService.BuscarPorId(id));
        } catch (RuntimeException e) {
            return buildErrorResponse("Espécie não encontrada", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma espécie existente")
    @ApiResponse(responseCode = "200", description = "Espécie atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Espécie não encontrada")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EspecieDto dto) {
        try {
            return ResponseEntity.ok(especieService.atualizar(id, dto));
        } catch (RuntimeException e) {
            return buildErrorResponse("Erro ao atualizar", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma espécie pelo ID")
    @ApiResponse(responseCode = "204", description = "Espécie removida com sucesso")
    @ApiResponse(responseCode = "404", description = "Espécie não encontrada")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            especieService.deletar(id);
            return ResponseEntity.ok("Espécie Removida");
        } catch (RuntimeException e) {
            return buildErrorResponse("Erro ao deletar", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String error, String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }
}