package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.VisitanteRequestDto;
import Api_de_zoologico.zoo.dtos.VisitanteResponseDto;
import Api_de_zoologico.zoo.models.Visitante;
import Api_de_zoologico.zoo.services.VisitanteService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visitantes")
@RequiredArgsConstructor
public class VisitanteController {

    private final VisitanteService visitanteService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<VisitanteResponseDto> criar(@RequestBody VisitanteRequestDto dto) {
        return ResponseEntity.ok(visitanteService.criarVisitante(dto));
    }

    @PreAuthorize("hasRole('VISITANTE') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<VisitanteResponseDto> listar() {
        return visitanteService.findAll();
    }

    @PreAuthorize("hasRole('VISITANTE') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            VisitanteResponseDto visitanteResponseDto = visitanteService.findById(id); // Agora você chama o método correto que retorna o DTO
            return ResponseEntity.ok(visitanteResponseDto); // Retorna o DTO
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


    @PreAuthorize("hasRole('VISITANTE') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<VisitanteResponseDto> atualizar(@PathVariable Long id, @RequestBody VisitanteRequestDto dto) {
        return ResponseEntity.ok(visitanteService.update(id, dto));
    }

    @PreAuthorize("hasRole('VISITANTE') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        visitanteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}