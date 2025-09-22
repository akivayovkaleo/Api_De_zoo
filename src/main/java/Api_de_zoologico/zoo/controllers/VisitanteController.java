package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.VisitanteRequestDto;
import Api_de_zoologico.zoo.dtos.VisitanteResponseDto;
import Api_de_zoologico.zoo.services.VisitanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visitantes")
@RequiredArgsConstructor
public class VisitanteController {

    private final VisitanteService visitanteService;

    @PostMapping
    public ResponseEntity<VisitanteResponseDto> criarVisitante(@RequestBody VisitanteRequestDto dto) {
        return ResponseEntity.ok(visitanteService.criarVisitante(dto));
    }
}