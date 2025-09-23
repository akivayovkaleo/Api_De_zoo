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

    @GetMapping
    public List<VisitanteResponseDto> listar() {
        return visitanteService.findAll();
    }

    @GetMapping("/{id}")
    public VisitanteResponseDto buscarPorId(@PathVariable Long id) {
        return visitanteService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitanteResponseDto> atualizar(@PathVariable Long id, @RequestBody VisitanteRequestDto dto) {
        return ResponseEntity.ok(visitanteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        visitanteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}