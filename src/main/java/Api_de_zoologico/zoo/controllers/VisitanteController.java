package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.VisitanteRequestDto;
import Api_de_zoologico.zoo.dtos.VisitanteResponseDto;
import Api_de_zoologico.zoo.services.VisitanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visitantes")
@RequiredArgsConstructor
public class VisitanteController {

    private final VisitanteService visitanteService;

    @PreAuthorize()
    @PostMapping
    public ResponseEntity<VisitanteResponseDto> criar(@RequestBody VisitanteRequestDto dto) {
        return ResponseEntity.ok(visitanteService.criarVisitante(dto));
    }

    @PreAuthorize("hasRole('VISITANTE') or hasRole('ADMIN')")
    @GetMapping
    public List<VisitanteResponseDto> listar() {
        return visitanteService.findAll();
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
    public ResponseEntity<VisitanteResponseDto> atualizar(@PathVariable Long id, @RequestBody VisitanteRequestDto dto) {
        return ResponseEntity.ok(visitanteService.update(id, dto));
    }

    @PreAuthorize("hasRole('VISITANTE') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        visitanteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}